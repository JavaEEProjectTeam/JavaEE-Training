package sms.util;

import java.util.List;
import java.util.Scanner;

import sms.entity.Customer;

/**
 * 充值工具，对会员进行充值
 * @author 王凯
 *
 */
public class RechargeTools {
	public static void main(String[] args) throws Exception {
		ObjectPersistent<List<Customer>> op = new ObjectPersistent<>();
		List<Customer> customers = op.getObject(InitialTools.customerFilePath);
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入客户的编号：");
		int cid = scanner.nextInt();
		for (Customer customer : customers) {
			if (customer.getCid() == cid) {
				System.out.print("请输入充值金额：");
				float charge = scanner.nextFloat() 
						+ customer.getAccountBalance(); //金额累加
				customer.setAccountBalance(charge);
				op.persistObject(customers, InitialTools.customerFilePath);
				System.out.println(customer.getCname() +"充值成功,卡内余额：" 
						+ customer.getAccountBalance() + "！");
				return;
			}
		}
		
		System.out.println("编号为" + cid + "的客户不存在，充值失败！");
	}
}