package cn.edu.nuc.onlinestore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验工具
 * @author 王凯
 *
 */
public class RegexUtility {
	
	/**
	 * 判断是否是整数，包括正整数和0
	 * @param str 匹配字符串
	 * @return 匹配结果
	 */
	public static boolean isInteger(String str) {
		return isPositiveInteger(str) || isZero(str);
	}
	
	/**
	 * 正则匹配
	 * @param regex 正则表达式
	 * @param orginal 匹配字符串
	 * @return 匹配结果
	 */
	private static boolean isMatch(String regex, String orginal){  
        if (orginal == null || orginal.trim().equals("")) {  
            return false;  
        }  
        Pattern pattern = Pattern.compile(regex);  
        Matcher isNum = pattern.matcher(orginal);  
        return isNum.matches();  
    }
	
	/**
	 * 判断是否是正整数
	 * @param orginal 匹配字符串
	 * @return 判断结果
	 */
	private static boolean isPositiveInteger(String orginal) {  
        return isMatch("^\\d*", orginal);  
    }  
	
	/**
	 * 判断是否是小数
	 * @param orginal 匹配字符串
	 * @return 判断结果
	 */
	private static boolean isFloat(String orginal) {  
        return isMatch("^\\d*.\\d*", orginal);  
    }
	
	/**
	 * 是否为0
	 * @param orginal 匹配字符串
	 * @return 判断结果
	 */
	private static boolean isZero(String orginal) {
		return isMatch("[+-]{0,1}0", orginal);
	}
  
	/**
	 * 判断是否是数字（包括正数、小数、0等情况）
	 * @param orginal
	 * @return 判断结果
	 */
    public static boolean isNumber(String orginal) {  
        return  isZero(orginal) || isPositiveInteger(orginal) || isFloat(orginal);  
    }   
}
