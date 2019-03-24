# itas_Mac

 获取MAC地址

## 使用示例
```             
String mac =null;                           
itas_Mac myMac=new itas_Mac();
MacAddress MacAddress = new MacAddress(myMac.getMac(context));
mac = MacAddress.getCont();
```
###  传入context，一般为MainActivity