package sms.controller;

import java.util.Scanner;

/**
 * 抽象控制器类
 * @author 王凯
 *
 */
public abstract class Controller {
	
	/**
	 * 执行业务流程方法，子类需要重写此方法定义自己的业务流程
	 */
	protected abstract void begin();
	
	/**
	 * 选择要执行的操作
	 * @return 选择的操作
	 */
	public int selectOptions(int max){
		Scanner scanner = new Scanner(System.in);
		int select = 0 ;
		while(true) {
			select = scanner.nextInt();
			if (select >= 0 && select <= max) {
				return select;
			}
			else {
				System.out.println("您输入的编号有误，请重新输入范围0-" + max + "内的整数！");
			}
		}
	}
}
