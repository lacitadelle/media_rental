package com.jkomala.bigproject.mediaObj;

public class XMLUtil {
    /**
     *
     * @param begin
     * @param end
     * @param line
     * @return substring of line, n indexes from the occurrence of <id> to the index before the occurrence of </id>
     */
    public static String parse(String begin, String end, String line) {
        return line.substring(line.indexOf(begin) + begin.length(), line.indexOf(end));
    }
}
