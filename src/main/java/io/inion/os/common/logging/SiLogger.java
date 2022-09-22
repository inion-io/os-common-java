package io.inion.os.common.logging;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.FormattedMessageFactory;
import org.apache.logging.log4j.message.SimpleMessageFactory;


@CellType(
    objectClass = SiLogger.SiLoggerObject.class,
    type = SiLogger.CELL_TYPE,
    uuid = SiLogger.CELL_UUID
)
public interface SiLogger extends SiCell<SiLogger, Logger> {

  String CELL_TYPE = "logger";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  void debug(String msg);

  void debug(String format, Object arg);

  void debug(String format, Object arg1, Object arg2);

  void debug(String format, Object... arguments);

  void debug(String msg, Throwable t);

  void debug(Throwable t);

  void debug(Marker marker, String msg);

  void debug(Marker marker, String format, Object arg);

  void debug(Marker marker, String format, Object arg1, Object arg2);

  void debug(Marker marker, String format, Object... arguments);

  void debug(Marker marker, String msg, Throwable t);

  void fatal(String msg);

  void fatal(String format, Object arg);

  void fatal(String format, Object arg1, Object arg2);

  void fatal(String format, Object... arguments);

  void fatal(String msg, Throwable t);

  void fatal(Throwable t);

  void fatal(Marker marker, String msg);

  void fatal(Marker marker, String format, Object arg);

  void fatal(Marker marker, String format, Object arg1, Object arg2);

  void fatal(Marker marker, String format, Object... arguments);

  void fatal(Marker marker, String msg, Throwable t);

  void error(String msg);

  void error(String format, Object arg);

  void error(String format, Object arg1, Object arg2);

  void error(String format, Object... arguments);

  void error(String msg, Throwable t);

  void error(Throwable t);

  void error(Marker marker, String msg);

  void error(Marker marker, String format, Object arg);

  void error(Marker marker, String format, Object arg1, Object arg2);

  void error(Marker marker, String format, Object... arguments);

  void error(Marker marker, String msg, Throwable t);

  void info(String msg);

  void info(String format, Object arg);

  void info(String format, Object arg1, Object arg2);

  void info(String format, Object... arguments);

  void info(String msg, Throwable t);

  void info(Throwable t);

  void info(Marker marker, String msg);

  void info(Marker marker, String format, Object arg);

  void info(Marker marker, String format, Object arg1, Object arg2);

  void info(Marker marker, String format, Object... arguments);

  void info(Marker marker, String msg, Throwable t);

  boolean isDebugEnabled();

  boolean isDebugEnabled(Marker marker);

  boolean isErrorEnabled();

  boolean isErrorEnabled(Marker marker);

  boolean isInfoEnabled();

  boolean isInfoEnabled(Marker marker);

  boolean isTraceEnabled();

  boolean isTraceEnabled(Marker marker);

  boolean isWarnEnabled();

  boolean isWarnEnabled(Marker marker);

  void trace(String msg);

  void trace(String format, Object arg);

  void trace(String format, Object arg1, Object arg2);

  void trace(String format, Object... arguments);

  void trace(String msg, Throwable t);

  void trace(Throwable t);

  void trace(Marker marker, String msg);

  void trace(Marker marker, String format, Object arg);

  void trace(Marker marker, String format, Object arg1, Object arg2);

  void trace(Marker marker, String format, Object... argArray);

  void trace(Marker marker, String msg, Throwable t);

  void warn(String msg);

  void warn(String format, Object arg);

  void warn(String format, Object... arguments);

  void warn(String format, Object arg1, Object arg2);

  void warn(String msg, Throwable t);

  void warn(Throwable t);

  void warn(Marker marker, String msg);

  void warn(Marker marker, String format, Object arg);

  void warn(Marker marker, String format, Object arg1, Object arg2);

  void warn(Marker marker, String format, Object... arguments);

  void warn(Marker marker, String msg, Throwable t);

  class SiLoggerObject extends SiCellObject<SiLogger, Logger> implements SiLogger {

    private SimpleMessageFactory simpleMessageFactory = new SimpleMessageFactory();
    private FormattedMessageFactory formattedMessageFactory = new FormattedMessageFactory();

    @Override
    public void fatal(String msg) {
      getCellValue().logMessage(Level.FATAL, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void fatal(String format, Object arg) {
      getCellValue().logMessage(Level.FATAL, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void fatal(String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.FATAL, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void fatal(String format, Object... arguments) {
      getCellValue().logMessage(Level.FATAL, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void fatal(String msg, Throwable t) {
      getCellValue().logMessage(Level.FATAL, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), t);
    }

    @Override
    public void fatal(Throwable t) {
      getCellValue().logMessage(Level.FATAL, null, SiLogger.class.getName(), getStackTraceElement(),
          null, t);
    }

    @Override
    public void fatal(Marker marker, String msg) {
      getCellValue().logMessage(Level.FATAL, marker, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void fatal(Marker marker, String format, Object arg) {
      getCellValue().logMessage(Level.FATAL, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void fatal(Marker marker, String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.FATAL, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void fatal(Marker marker, String format, Object... arguments) {
      getCellValue().logMessage(Level.FATAL, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void fatal(Marker marker, String msg, Throwable t) {
      getCellValue().logMessage(Level.FATAL, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(msg), t);
    }

    @Override
    public void info(String msg) {
      getCellValue().logMessage(Level.INFO, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void info(String format, Object arg) {
      getCellValue().logMessage(Level.INFO, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.INFO, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void info(String format, Object... arguments) {
      getCellValue().logMessage(Level.INFO, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void info(String msg, Throwable t) {
      getCellValue().logMessage(Level.INFO, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), t);
    }

    @Override
    public void info(Throwable t) {
      getCellValue().logMessage(Level.INFO, null, SiLogger.class.getName(), getStackTraceElement(),
          null, t);
    }

    @Override
    public void info(Marker marker, String msg) {
      getCellValue().logMessage(Level.INFO, marker, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
      getCellValue().logMessage(Level.INFO, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.INFO, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
      getCellValue().logMessage(Level.INFO, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
      getCellValue().logMessage(Level.INFO, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(msg), t);
    }

    @Override
    public void debug(String msg) {
      getCellValue().logMessage(Level.DEBUG, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void debug(String format, Object arg) {
      getCellValue().logMessage(Level.DEBUG, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.DEBUG, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void debug(String format, Object... arguments) {
      getCellValue().logMessage(Level.DEBUG, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void debug(String msg, Throwable t) {
      getCellValue().logMessage(Level.DEBUG, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), t);
    }

    @Override
    public void debug(Throwable t) {
      getCellValue().logMessage(Level.DEBUG, null, SiLogger.class.getName(), getStackTraceElement(),
          null, t);
    }

    @Override
    public void debug(Marker marker, String msg) {
      getCellValue().logMessage(Level.DEBUG, marker, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
      getCellValue().logMessage(Level.DEBUG, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.DEBUG, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
      getCellValue().logMessage(Level.DEBUG, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
      getCellValue().logMessage(Level.DEBUG, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(msg), t);
    }

    @Override
    public void trace(String msg) {
      getCellValue().logMessage(Level.TRACE, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void trace(String format, Object arg) {
      getCellValue().logMessage(Level.TRACE, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.TRACE, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void trace(String format, Object... arguments) {
      getCellValue().logMessage(Level.TRACE, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void trace(String msg, Throwable t) {
      getCellValue().logMessage(Level.TRACE, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), t);
    }

    @Override
    public void trace(Throwable t) {
      getCellValue().logMessage(Level.TRACE, null, SiLogger.class.getName(), getStackTraceElement(),
          null, t);
    }

    @Override
    public void trace(Marker marker, String msg) {
      getCellValue().logMessage(Level.TRACE, marker, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
      getCellValue().logMessage(Level.TRACE, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.TRACE, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void trace(Marker marker, String format, Object... arguments) {
      getCellValue().logMessage(Level.TRACE, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
      getCellValue().logMessage(Level.TRACE, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(msg), t);
    }

    @Override
    public void error(String msg) {
      getCellValue().logMessage(Level.ERROR, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void error(String format, Object arg) {
      getCellValue().logMessage(Level.ERROR, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.ERROR, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void error(String format, Object... arguments) {
      getCellValue().logMessage(Level.ERROR, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void error(String msg, Throwable t) {
      getCellValue().logMessage(Level.ERROR, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), t);
    }

    @Override
    public void error(Throwable t) {
      getCellValue().logMessage(Level.ERROR, null, SiLogger.class.getName(), getStackTraceElement(),
          null, t);
    }

    @Override
    public void error(Marker marker, String msg) {
      getCellValue().logMessage(Level.ERROR, marker, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
      getCellValue().logMessage(Level.ERROR, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.ERROR, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
      getCellValue().logMessage(Level.ERROR, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
      getCellValue().logMessage(Level.ERROR, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(msg), t);
    }

    @Override
    public void warn(String msg) {
      getCellValue().logMessage(Level.WARN, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void warn(String format, Object arg) {
      getCellValue().logMessage(Level.WARN, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.WARN, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void warn(String format, Object... arguments) {
      getCellValue().logMessage(Level.WARN, null, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void warn(String msg, Throwable t) {
      getCellValue().logMessage(Level.WARN, null, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), t);
    }

    @Override
    public void warn(Throwable t) {
      getCellValue().logMessage(Level.WARN, null, SiLogger.class.getName(), getStackTraceElement(),
          null, t);
    }

    @Override
    public void warn(Marker marker, String msg) {
      getCellValue().logMessage(Level.WARN, marker, SiLogger.class.getName(), getStackTraceElement(),
          simpleMessageFactory.newMessage(msg), null);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
      getCellValue().logMessage(Level.WARN, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg), null);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
      getCellValue().logMessage(Level.WARN, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arg1, arg2), null);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
      getCellValue().logMessage(Level.WARN, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(format, arguments), null);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
      getCellValue().logMessage(Level.WARN, marker, SiLogger.class.getName(), getStackTraceElement(),
          formattedMessageFactory.newMessage(msg), t);
    }

    @Override
    public boolean isDebugEnabled() {
      return getCellValue().isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
      return getCellValue().isDebugEnabled(marker);
    }

    @Override
    public boolean isErrorEnabled() {
      return getCellValue().isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
      return getCellValue().isErrorEnabled(marker);
    }

    @Override
    public boolean isInfoEnabled() {
      return getCellValue().isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
      return getCellValue().isInfoEnabled(marker);
    }

    @Override
    public boolean isTraceEnabled() {
      return getCellValue().isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
      return getCellValue().isTraceEnabled(marker);
    }

    @Override
    public boolean isWarnEnabled() {
      return getCellValue().isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
      return getCellValue().isWarnEnabled(marker);
    }


    private StackTraceElement getStackTraceElement() {
      StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
      return stackTraceElements.length > 2 ? stackTraceElements[2] : stackTraceElements[1];
    }
  }
}
