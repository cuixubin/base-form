package com.dlshouwen.core.base.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 编写示例程序经常使用的工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-12 9:24:32
 */
public class DemoUtils {

	/** 基础字符 */
	private static String base = "abcdefghijklmnopqrstuvwxyz0123456789";

	/** 常用姓氏 */
	private static String firstName = "李王张刘陈杨赵黄周吴徐孙胡朱高林何郭马罗梁宋郑谢韩唐冯于董萧程曹袁邓许傅沈曾彭吕苏卢蒋蔡贾丁魏薛叶阎余潘杜戴夏锺汪田任姜范方石姚谭廖邹熊金陆郝孔白崔康毛邱秦江史顾侯邵孟龙万段雷钱汤尹黎易常武乔贺赖龚文";

	/** 常用男性名称 */
	private static String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";

	/** 常用女性名称 */
	private static String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";

	/** 常用Email后缀 */
	private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@live.cn,@outlook.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");

	/** 手机号码前三位 */
	private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

	/**
	 * 获取随机数
	 * <p>用于获取start到end之间的随机数，start、end亦可能被随机到
	 * @param start 开始数值
	 * @param end 结束数值
	 * @return 介于开始结束之间的随机数，包含开始及结束数值
	 */
	public static int getRandomNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

	/**
	 * 随机生成Email
	 * <p>用于随机生成Email信息，长度介于lMin及lMax之间，包含lMin及lMax，Email后缀亦计入长度
	 * @param lMin Email最小长度
	 * @param lMax Email最大长度
	 * @return Email地址
	 */
	public static String getEmail(int lMin, int lMax) {
		int length = getRandomNum(lMin, lMax);
		StringBuffer email = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = (int) (Math.random() * base.length());
			email.append(base.charAt(number));
		}
		email.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
		return email.toString();
	}

	/**
	 * 随机生成手机号码
	 * <p>用于随机生成手机号码
	 * @return 手机号码
	 */
	public static String getTelephone() {
		int index = getRandomNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getRandomNum(1, 888) + 10000).substring(1);
		String thrid = String.valueOf(getRandomNum(1, 9100) + 10000).substring(1);
		return first + second + thrid;
	}

	/** 临时存储的性别，每当获取一次中文名称时会按中文名称获取性别，可能的值：男、女 */
	public static String name_sex = "";

	/**
	 * 随机生成中文名
	 * <p>用于随机生成中文名，生成过程中将随机性别，随机到的性别存储在name_sex属性中
	 * @return 中文名
	 */
	public static String getChineseName() {
		int index = getRandomNum(0, firstName.length() - 1);
		String first = firstName.substring(index, index + 1);
		int sex = getRandomNum(0, 1);
		String str = boy;
		int length = boy.length();
		if (sex == 0) {
			str = girl;
			length = girl.length();
			name_sex = "女";
		} else {
			name_sex = "男";
		}
		index = getRandomNum(0, length - 1);
		String second = str.substring(index, index + 1);
		int hasThird = getRandomNum(0, 1);
		String third = "";
		if (hasThird == 1) {
			index = getRandomNum(0, length - 1);
			third = str.substring(index, index + 1);
		}
		return first + second + third;
	}

	/**
	 * 中文转拼音首字母
	 * <p>用于将字符串中的中文转化为拼音首字母,其他字符不变
	 * @param chinese 需要转换为拼音的汉字字符串
	 * @return 转换拼音首字母后的字符串
	 */
	public static String getFirstSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (temp != null) {
						pybf.append(temp[0].charAt(0));
					} else {
						pybf.append(arr[i]);
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 中文转拼音
	 * <p>用于将字符串中的中文转化为拼音,其他字符不变
	 * @param chinese 需要转换为拼音的汉字字符串
	 * @return 转换拼音后的字符串
	 */
	public static String getFullSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] > 128) {
					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} else {
					pybf.append(arr[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pybf.toString();
	}

}