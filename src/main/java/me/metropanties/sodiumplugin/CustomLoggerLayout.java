package me.metropanties.sodiumplugin;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.LayoutBase;
import org.jetbrains.annotations.NotNull;

public class CustomLoggerLayout extends LayoutBase<ILoggingEvent> {

    private static final String PREFIX = AnsiColors.GREY + "[" + AnsiColors.BLUE + "Sodium" + AnsiColors.GREY + "] ";

    @Override
    public String doLayout(@NotNull ILoggingEvent event) {
        String thread = AnsiColors.GREY + "[" + AnsiColors.GREEN + event.getThreadName() + AnsiColors.GREY + "]" +
                AnsiColors.RESET + " ";

        String level;
        if (event.getLevel().equals(Level.INFO)) {
            level = AnsiColors.GREY + "[" + AnsiColors.GREEN + "{level}" + AnsiColors.GREY + "]" +
                    AnsiColors.RESET + " ";
        } else if (event.getLevel().equals(Level.WARN)) {
            level = AnsiColors.GREY + "[" + AnsiColors.YELLOW + "{level}" + AnsiColors.GREY + "]" +
                    AnsiColors.RESET + " ";
        } else if (event.getLevel().equals(Level.ERROR)) {
            level = AnsiColors.GREY + "[" + AnsiColors.RED + "{level}" + AnsiColors.GREY + "]" +
                    AnsiColors.RESET + " ";
        } else {
            level = AnsiColors.GREY + "[" + AnsiColors.WHITE + "{level}" + AnsiColors.GREY + "]" +
                    AnsiColors.RESET + " ";
        }
        level = level.replace("{level}", event.getLevel().toString());

        if (event.getThrowableProxy() != null) {
            StringBuilder builder = new StringBuilder(event.getThrowableProxy().getMessage() + " ");
            StackTraceElementProxy[] stackTraceElements = event.getThrowableProxy().getStackTraceElementProxyArray();
            for (StackTraceElementProxy element : stackTraceElements) {
                builder.append(element).append("\n");
            }

            return PREFIX + thread + level + event.getFormattedMessage() + "\n" + AnsiColors.RED +
                    builder.substring(0, builder.length() - 1) + AnsiColors.RESET;
        }

        return PREFIX + thread + level + event.getFormattedMessage();
    }

    @SuppressWarnings("unused")
    public static class AnsiColors {

        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";
        public static final String GREY = "\u001B[1;30m";

    }

}
