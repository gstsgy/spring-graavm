# 使用 Paketo 的微型运行环境
FROM paketobuildpacks/run-jammy-tiny

WORKDIR /app

# 注意：Paketo 镜像默认使用 cnb (UID 1000) 用户运行，更安全
# 建议将文件权限拷贝给该用户
COPY --chown=1000:1000 webapi/build/native/nativeCompile/webapi-app /app/server

USER 1000:1000

ENTRYPOINT ["/app/server"]
