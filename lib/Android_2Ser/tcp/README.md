# itas_tcp
实现tcp通讯，利用 AsyncTask实现连接等异步操作，提供TcpLister接口处理通讯时的各种结果
## 功能有
#### 发送消息
#### 接收消息
#### 提供当发送失败，连接失败，断开连接，收到消息，发送成功时的处理接口

## 使用示例
### 构造接口
```
private TcpLister tcpLister = new TcpLister() {
        @Override
        public void onReceive(String msg) {
            textView.setText(textView2.getText() + msg + "\n");
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectFailed() {
            Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTcpColosed() {
            textView.setText(textView2.getText() + "连接已断开！" + "\n");
            Toast.makeText(MainActivity.this, "连接已断开！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectSuccess() {
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void sendSuccess() {
            Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
        }
    };
```

### 创建连接
``` 
private itas_Tcp tcp;
tcp = new itas_Tcp("192.168.123.141", 1331, tcpLister);
tcp.createSocket();
```

### 发送消息
``` 
private itas_Tcp tcp;
public EditText edit;
edit = (EditText) findViewById(R.id.edit);
tcp = new itas_Tcp("192.168.123.141", 1331, tcpLister);
tcp.createSocket();
tcp.sendMsg(edit.getText().toString());
```

### 关闭连接
```
private itas_Tcp tcp;
tcp = new itas_Tcp("192.168.123.141", 1331, tcpLister);
tcp.createSocket();
tcp.closeSocket();
```



