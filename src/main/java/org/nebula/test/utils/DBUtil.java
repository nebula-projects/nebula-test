/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nebula.test.utils;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {

  private final static Log log = LogFactory.getLog(DBUtil.class);

  private Connection conn;

  public DBUtil() {
    try {
      PropertiesConfiguration config = new PropertiesConfiguration("test.properties");
      Class.forName("com.mysql.jdbc.Driver").newInstance();

      conn =
          DriverManager.getConnection(config.getString("dbUrl"), config.getString("dbUsername"),
                                      config.getString("dbPassword"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void removeAllData() {
    removeData("workflow_timers");
    removeData("activity_timers");
    removeData("registrations");
    removeData("heartbeats");
    removeData("events");
    removeData("history_events");
  }


  public void removeEventsForInstance(String instanceId) {
    removeData("events", "instanceId", instanceId);
  }

  public void removeEventsForWorkflow(String registrationId) {
    removeData("events", "registrationId", registrationId);
  }

  public void removeHistoryEventsForWorkflow(String registrationId) {
    removeData("history_events", "registrationId", registrationId);
  }

  public void removeWorkflowTimer(String registrationId) {
    removeData("workflow_timers", "registrationId", registrationId);
  }

  public void removeHeartbeatsForWorkflow(String registrationId) {
    removeData("heartbeats", "registrationId", registrationId);
  }

  public void removeRegistrationsForUser(String user) {
    removeData("registrations", "user", user);
  }

  private void removeData(String tableName) {
    removeData(tableName, null, null);
  }

  private void removeData(String tableName, String columnName, String condition) {

    PreparedStatement stmt = null;
    try {

      if (columnName == null) {
        stmt =
            conn.prepareStatement(
                "DELETE FROM " + tableName);
      } else {
        stmt =
            conn.prepareStatement(
                "DELETE FROM " + tableName + " WHERE " + columnName + "=?");
        stmt.setString(1, condition);
      }

      stmt.executeUpdate();

    } catch (Exception e) {
      log.error("Failed to removeData.", e);
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (Exception e) {
          log.error("Failed to close statement.", e);
        }
      }
    }

  }

  public void removeAllEvents() {
    removeData("events");
  }

  public int getNumbersOfCompletedInstanceForWorkflow(String registrationId) {

    return getNumbersOfEvents("events", "WorkflowCompletedEvent", registrationId)
           + getNumbersOfEvents("history_events", "WorkflowCompletedEvent", registrationId);
  }


  public int getNumbersOfStartedInstanceForWorkflow(String registrationId) {

    return getNumbersOfEvents("events", "WorkflowScheduledEvent", registrationId)
           + getNumbersOfEvents("history_events", "WorkflowScheduledEvent", registrationId);
  }

  private int getNumbersOfEvents(String eventTable, String eventType, String registrationId) {
    int eventNumbers = 0;

    PreparedStatement stmt = null;
    try {

      if (registrationId == null) {
        stmt =
            conn.prepareStatement("SELECT count(1) FROM " + eventTable + " WHERE  eventType= ? ");
      } else {
        stmt =
            conn.prepareStatement("SELECT count(1) FROM " + eventTable
                                  + " WHERE  eventType= ? AND registrationId= ? ");
        stmt.setString(2, registrationId);
      }
      stmt.setString(1, eventType);

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        eventNumbers += rs.getInt(1);
      }

    } catch (Exception e) {
      log.error("Failed to getNumbersOfEvents.", e);
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (Exception e) {
          log.error("Failed to close statement .", e);
        }
      }
    }

    return eventNumbers;
  }

  public int getNumbersOfCompletedInstance() {

    return getNumbersOfEvents("events", "WorkflowCompletedEvent", null) + getNumbersOfEvents(
        "history_events", "WorkflowCompletedEvent", null);
  }

  public void removeAllTimers() {
    removeData("workflow_timers");
    removeData("activity_timers");
  }


  public String getWorkflowRegistrationId(String username, String workflowName, String version) {

    PreparedStatement stmt = null;
    try {

      stmt =
          conn.prepareStatement(
              "SELECT id FROM registrations WHERE user= ? AND name = ? AND version=? AND type='WORKFLOW'");
      stmt.setString(1, username);
      stmt.setString(2, workflowName);
      stmt.setString(3, version);

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        return rs.getString(1);
      }

    } catch (Exception e) {
      log.error("Failed to getWorkflowRegistrationId.", e);
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (Exception e) {
          log.error("Failed to close statement .", e);
        }
      }
    }

    return null;
  }

  public boolean existInHistoryEventsTable(String instanceId) {

    PreparedStatement stmt = null;
    try {

      stmt =
          conn.prepareStatement(
              "SELECT id FROM history_events WHERE instanceId= ?");
      stmt.setString(1, instanceId);

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        return true;
      }

    } catch (Exception e) {
      log.error("Failed to getWorkflowRegistrationId.", e);
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (Exception e) {
          log.error("Failed to close statement .", e);
        }
      }
    }

    return false;

  }

}
