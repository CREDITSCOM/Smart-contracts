# Description
An escrow smart contract is an algorithm that runs within the framework of the Credits decentralized blockchain platform. By relying on the given smart contract, users have the opportunity to lock funds for a predetermined period of time.
# Functions
* Withholding funds on the address of the smart contract in accordance with the time period set within the code;
* Depositing funds to the address of the smart contract;
* Withdrawing funds from the address of the smart contract after the expiration of the freezing period (the initiation of a withdrawal is possible by any user who has funds on the public account to pay the commission for the execution of the smart contract);
* Receiving information about the sender, recipient, date of unlocking and deposited amount of funds.
# Instruction for use
1. Convert the source code of the smart contract and add the necessary variables:
* private String beneficiary = "reciever_wallet";
Instead of the text “receiver_wallet”, you must insert the public address of the “Recipient” wallet, which should be created within the Credits blockchain platform. It will be possible to withdraw funds that will be deposited to the address of the smart contract only at this public address.
* final int unix_time = date_time;
Instead of the text “date_time”, you must insert the date and time after which the funds deposited into the smart contract can be withdrawn. The date and time must be converted to the unix format using the converter.
2. Deploy a smart contract using Web Wallet or Desktop Wallet services. After the deployment of the smart contract, the data entered in paragraph 1 will be rendered immutable.

3. Place the desired amount on the deposit of the smart contract by sending to the public address of the smart contract. In the future, it will be impossible to change the amount sent!

4. The “Recipient” can try to receive the full amount at any time using the method - tryWithdraw (). When performing this method, there are three possible scenarios:
* If the time for unlocking has not yet come - a message about when the funds are unlocked will be displayed;
* If the funds are unlocked, the amount will be transferred to the wallet of the Recipient;
* If the deposit has already been received, this will be stated and the contract will be transferred into a “closed” state.
5. In addition to the “Recipient”, any user can also call the tryWithdraw () method. If the conditions for the date of unlocking are met, the funds will be transferred to the account of the beneficiary of the contract. It is not necessary to be authorized under the Recipient’s private key for correct execution of the withdrawal process.

6. The set of get * () methods allows users to learn about the state of the deposit without any commissions:
* Sender funds
* Beneficiary
* Unlock date
* Deposit amount
