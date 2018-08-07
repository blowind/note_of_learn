// 0地址，用于在区块链上注册一个合约（创建一个特殊的交易）
0x0000000000000000000000000000000000000000 




// Version of Solidity compiler this program was written for
pragma solidity ^0.4.19;

// Our first contract is a faucet!
contract Faucet {

	// Give out ether to anyone who asks
	function withdraw(uint withdraw_amount) public {

    	// Limit withdrawal amount
    	require(withdraw_amount <= 100000000000000000);

    	// Send the amount to the address that requested it
    	msg.sender.transfer(withdraw_amount);
    }

	// 触发contract不指定调用函数时，默认会调用的函数，
	// 名字不重要所以没有函数名，除了接收ether一般没什么其他作用
	function () public payable {}

}