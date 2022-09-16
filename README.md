## asshole
内网穿透 参考frp写的JAVA版本(比较`臃肿`)
# 使用方法
* Server: java -cp asshole-1.0-SNAPSHOT.jar cs.Server -c Asshole.ini
* Client: java -cp asshole-1.0-SNAPSHOT.jar cs.Client -c Asshole.ini
```ini
[server]
server_host=serverIp #服务IP
server_port=serverPort #服务端端口
token=Token  #token
[local]
local_host=localHost  #本地IP
local_port=localPort  #本地端口
proxy_port=remote2LocalPort #远程端口(服务端会开启这个端口将数据转发到本地)
```
