package com.lxp.global.support;

public class AsciiArtProvider {

    /**
     * Returns a multi-line string of ASCII art that resembles a video player UI. This can be used as mock data for a
     * lecture's path for console-based UIs.
     *
     * @return A string containing the ASCII art.
     */
    public static String getSampleVideoAscii() {
        return """
                +----------------------------------------------------------+
                |                                                          |
                |                                                          |
                |                      .--.                                |
                |                     |o_o |                               |
                |                     |:_/ |                               |
                |                    //   \\\\                               |
                |                   (|     | )                             |
                |                  /'\\\\_   _/`\\\\                           |
                |                  \\\\___)=(___/                            |
                |                                                          |
                |      <||>  |----------------o--------------|  + O -      |
                |     VIDEO LECTURE: 강의 재생중,,,                           |
                +----------------------------------------------------------+
                """;
    }

    /**
     * Returns a simple animated ASCII art sequence. Each string in the array is a frame.
     *
     * @return An array of strings representing animation frames.
     */
    public static String[] getAnimatedSampleVideoAscii() {
        return new String[]{
                """
+----------------------------------+
|                                  |
|           (>'-')>                |
|                                  |
+----------------------------------+
""",
                """
+----------------------------------+
|                                  |
|           <('-'<)                |
|                                  |
+----------------------------------+
""",
                """
+----------------------------------+
|                                  |
|           ^('-')^                |
|                                  |
+----------------------------------+
"""
        };
    }
}
