# java-webhook

Java实现的Webhook服务端，用于监听Github平台发送的Webhook请求，自动执行脚本命令，并记录日志文件

## 特点

1. 项目体积小巧，不依赖第三方库
2. 支持脚本回写日志文件
3. 支持外部配置文件，更改相关配置

## 运行环境

JRE：== 8

## 部署步骤

> 请先在系统中安装Java运行环境。
>
> 服务器终端执行 `java -version` ，在输出中看到有 `1.8`，则安装Java运行环境成功。

### 开始部署

1. 下载最新的软件包：[Releases](https://github.com/liaocp66/java-webhook/releases)
2. 执行解压命令`tar zxvf java-webhook.tar.gz`，并进入 webhook 目录
3. 执行 `run.sh` 脚本

> 注意：运行的 *.sh 文件都需要有执行权限。使用命令赋权：`chmod +x <脚本名称>`

此时服务端将监听：端口：`4567`，地址：`/webhook`

### 配置地址端口

找到 `config.properties` 文件，更改里面的配置项即可。

* url：地址。默认为：/webhook
* port：端口。默认为：4567
* secret：密钥。默认为：123456

## cURL 测试

粘贴下面命令在服务器终端执行，如果返回的字符串`ok`，则说明部署成功

```shell
curl --location --request POST 'http://localhost:4567/webhook' \
--header 'X-Hub-Signature: sha1=8cded2cfd0eed0e7a4457602e40d89409f133c47' \
--header 'X-GitHub-Event: ping' \
--header 'X-GitHub-Delivery: cad15200-e8d0-11ec-8ecf-7ed4a33ce6f1' \
--header 'X-Hub-Signature-256: sha256=cb501e835b2c39ff70b72e399ceaae1ea870ac9dac3218178f6e63722a151c72' \
--header 'Content-Type: application/json' \
--data-raw '123123'
```

## 开源协议

[MIT License](https://github.com/liaocp66/java-webhook/blob/main/LICENSE)

