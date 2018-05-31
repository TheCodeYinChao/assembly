/**
 * Project Name:saas-common
 * File Name:MDT.java
 * Package Name:com.ccop.common.util
 * Date:2017年11月13日下午7:09:00
 * Copyright (c) 2017, WangLZ All Rights Reserved.
 *
*/

package cn.bainuo.util;

/**
 * ClassName:MDT <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月13日 下午7:09:00 <br/>
 * 
 * @author WangLZ
 * @version
 * @since JDK 1.6
 * @see
 */
public enum MDT {

	accessHttp("accessHttp", 1), accessTcp("accessTCP", 2), ts("ts", 3), ws("ws", 4), rm("rm", 5), gw("gw",
			6), bill("bill", 7), dsr("dsr", 8), dscud("dscud", 9);

	private String name;
	private int idx;

	private MDT(String name, int idx) {
		this.name = name;
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public int getIdx() {
		return idx;
	}

}
