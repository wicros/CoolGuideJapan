package jp.co.jpmobile.coolguidejapan.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;


/**
 * 文字列 ユーティリティクラス.
 *
 * @author User
 */
public final class StringUtils {
    /** 行頭、行末の BR タグにマッチするパターン. */
    private static final Pattern TRIMBRTAG_PATTERN = Pattern.compile("(^<br\\s*[/]*>|<br\\s*[/]*>$)", Pattern.CASE_INSENSITIVE);
    /** Solr クエリーでエスケープが必要な文字にマッチするパターン. */
    private static final Pattern SOLR_ESCAPE_PATTERN = Pattern.compile("[¥+\\-¥!¥(¥)¥{¥}\\[\\]¥^~¥*¥?:\"]|&{2}|\\|{2}|[\\\\]", Pattern.UNIX_LINES);
    /** 日時指定フォーマット */
    public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_PATTERN_SHORT = "yyyy/MM/dd";



    /**
     * 文字列が空文字かどうかを取得します. NULL または、空文字の場合は true を、それ以外の場合は false を返します.
     *
     * @param str
     *            判定する文字列
     * @return NULL または、空文字の場合は true
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() <= 0) return true;
        return false;
    }

    /**
     * date型をString型に変換します．フォーマットが指定できます．
     *
     * @param date
     * @param format
     * @return 変換後の文字列
     */
    public static String getDateStr(Date date, String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        String term = sdf.format(date);
        return term;
    }

    /**
     * 後ろから何文字かを伏せ字にします．"123456", 3, 1が引数の場合，"123**6"を返します．
     *
     * @param string
     * @param hideCharsFromLast
     *            : 後ろから何文字ぶんを伏せ字にするか指定します．
     * @param showCharsFromLast
     *            : 後ろから何文字ぶんは伏せ字にしないかを指定します．
     */
    public static String hideAllButLast(String string, final int hideCharsFromLast, final int showCharsFromLast) {
        if (isEmpty(string)) return "";

        final int length = string.length();
        if (showCharsFromLast < 0) throw new IllegalArgumentException("cannot show last chars: " + showCharsFromLast);
        if (hideCharsFromLast < showCharsFromLast) throw new IllegalArgumentException("hideCharsFromLast: " + hideCharsFromLast + " < showCharsFromLast: " + showCharsFromLast);
        if (string.length() < hideCharsFromLast) throw new IllegalArgumentException(string + " .length() < hideCharsFromLast: " + hideCharsFromLast);
        final String lastHalf = string.substring(length - hideCharsFromLast);// 後ろhideCharsFromLast文字ぶんを取り出す
        final String firstHalf = string.substring(0, length - hideCharsFromLast); // 後ろhideCharsFromLast文字より前の部分を取り出す

        String hidden = lastHalf.replaceAll(".", "*"); // いったん全部伏せ字に
        StringBuilder second = new StringBuilder(hidden);
        int hiddenLength = hidden.length();
        for (int i = showCharsFromLast; 1 <= i; i--) {
            // 一部を元に戻す
            second.setCharAt(hiddenLength - i, lastHalf.charAt(hiddenLength - i));
        }
        return firstHalf + second.toString();
    }

    /**
     * 文字列をjointで結合します．ただし左右どちらかが空文字列の場合，jointは使いません．
     *
     * @param string1
     * @param string2
     * @param joint
     */
    public static String joinStrings(String string1, String string2, String joint) {
        if (string1 == null) string1 = "";
        if (string2 == null) string2 = "";

        if (StringUtils.isEmpty(string1) || StringUtils.isEmpty(string2)) joint = "";

        StringBuilder sb = new StringBuilder();
        sb.append(string1).append(joint).append(string2);
        return sb.toString();
    }

    /**
     * 与えられた文字列を，半角max文字以下に切り詰めます。切り捨て部分がある場合，"…"を末尾に追加します。
     *
     * @param input
     * @param max
     * @param padLeft
     *            : trueの場合，末尾に空白を入れて，max文字ちょうどになるようにします．
     */
    public static String ellipsize(String input, int max, boolean padLeft) {
        if (input == null || input.length() <= max / 2) { // max / 2未満だったら全角でも確実に入るので，処理スキップ．
            return (padLeft ? padLeft(input, max) : input);
        }
        StringBuilder result = new StringBuilder();
        int currentLength = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            currentLength += (isHalfWidth(c) ? 1 : 2);

            if (currentLength >= max) {
                if (currentLength == max && i == input.length() - 1) {
                    result.append(c); // これで最後の文字なのであれば，追加して終了
                } else {
                    result.append(ELLIPSIZER);
                }
                break;
            } else {
                result.append(c);
            }
        }
        return (padLeft ? padLeft(result.toString(), max) : result.toString());
    }

    public static String ellipsizeMiddle(String input, int max, boolean padLeft) {
        if (input == null || input.length() < max) {
            return (padLeft ? padLeft(input, max) : input);
        }
        int charactersAfterEllipsis = max / 2;
        int charactersBeforeEllipsis = max - ELLIPSIZER.length() - charactersAfterEllipsis;
        return input.substring(0, charactersBeforeEllipsis) + ELLIPSIZER + input.substring(input.length() - charactersAfterEllipsis);
    }

    public static String getCRLF() {
        return System.getProperty("line.separator");
    }

    public static final String ELLIPSIZER = "…"; // "..."ではなく"…"がAndroid推奨

    public static boolean isHalfWidth(char c) {
        return (c <= '\u007e') || // 英数字
                (c == '\u00a5') || // \記号
                (c == '\u203e') || // ~記号
                (c >= '\uff61' && c <= '\uff9f'); // 半角カナ
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    /**
     * @see <a
     *      href="http://yuki312.blogspot.jp/2013/05/androidtolowercasetouppercase.html">参考ページ</a>
     * @param str
     */
    public static String toLowerCase(String str) {
        // ロケールを指定しない場合，大文字-小文字対応が英語と異なっている言語に対応できないので
        return str.toLowerCase(Locale.ENGLISH);
    }

    public static String toUpperCase(String str) {
        // ロケールを指定しない場合，大文字-小文字対応が英語と異なっている言語に対応できないので
        return str.toUpperCase(Locale.ENGLISH);
    }

    /**
     * {@link String#format(String, Object...)}の代わりに使います．<br>
     * 遅いので，文字列結合には用いないこと．
     *
     * @param format
     * @param args
     * @return string
     */
    public static String format(String format, Object... args) {
        return String.format(Locale.ENGLISH, format, args);
    }

    private static final String DEFAULT_REPLACER_FOR_CRLF = " ";
    /** 先頭の改行コードは削除する */
    @SuppressWarnings("unused")
    private static final Pattern PATTERN_CRLF = Pattern.compile("^[" + Pattern.quote(getCRLF()) + "]*");


    /**
     * 引数がnullの場合に空文字を返却します。
     * @return
     */
    public static String formatNullValue(String str) {
        return isEmpty(str)?"":str;
    }

    public static boolean isStringBiggerEqualThan(String str1,String str2){
        int compare = str1.compareTo(str2);
        if(compare < 0){
            return  false;
        }else if(compare > 0 ){
            return true;
        }else{
            return true;
        }
    }

    public static boolean isNum(Context context,String string){
            String regex = context.getString(R.string.num_regx);
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(string);
            return m.find();
    }

    public static  boolean isPassword(Context context,String string){
        if (string == null){
            return false;
        }
        String string1 = string.trim();
        String regex = context.getString(R.string.password_regx);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string1);
        return m.find() && string1.length() > 5;
    }

    public static  boolean isEmailAddress(Context context,String string){
        if (string == null){
            return false;
        }

        String string1 = string.trim();
        String regex = Urlconf.EMAIL_REG;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string1);
        return m.find() && string1.length() > 0;

    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
