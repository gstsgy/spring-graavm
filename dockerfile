# 直接从运行环境开始
FROM debian:bookworm-slim
WORKDIR /app
  # 手动将本地 build 好的二进制文件拷贝进去
COPY webapi/build/native/nativeCompile/webapi-app /app/server
RUN chmod +x /app/server
ENTRYPOINT ["/app/server"]
