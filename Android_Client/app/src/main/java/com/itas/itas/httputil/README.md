# HttpUtil
包装了mac、登录、注册验证的http逻辑处理，返回json解析后的类

## 使用示例
首先需要实现HttpStatusLister接口

```java
    private HttpStatusLister lister = new HttpStatusLister() {
        @Override
        public void onGetMacStatus(MacStatus macStatus) {
			根据返回的macStatus，编写相关逻辑
        }

        @Override
        public void onGetLoginStatus(LoginStatus loginStatus) {
			根据返回的loginStatus，编写相关逻辑
        }

        @Override
        public void onGetSignStatus(SignStatus signStatus) {
			根据返回的signStatus，编写相关逻辑
        }
    };
```

发起mac的http请求
```java
HttpUtil.judgeMAC("http://192.168.123.90:8080/Client/",MainActivity.this,lister);
```

发起Login的http请求

```java
HttpUtil.login("http://192.168.123.90:8080/Client/",passwd,lister);
```

发起Login的http请求
```java
HttpUtil.sign("http://192.168.123.90:8080/Client/",passwd,name,lister);
```