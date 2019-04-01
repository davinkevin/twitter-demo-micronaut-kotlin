FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/twitter-demo-kotlin
WORKDIR /home/gradle/twitter-demo-kotlin
RUN ./gradlew build

FROM oracle/graalvm-ce:1.0.0-rc11 as graalvm
COPY --from=builder /home/gradle/twitter-demo-kotlin/ /home/gradle/twitter-demo-kotlin/
WORKDIR /home/gradle/twitter-demo-kotlin
RUN java -cp build/libs/*-all.jar \
            io.micronaut.graal.reflect.GraalClassLoadingAnalyzer \
            reflect.json
RUN native-image --no-server \
                 --class-path /home/gradle/twitter-demo-kotlin/build/libs/*-all.jar \
                 -H:ReflectionConfigurationFiles=/home/gradle/twitter-demo-kotlin/reflect.json \
                 -H:EnableURLProtocols=http \
                 -H:IncludeResources='logback.xml|application.yml|META-INF/services/*.*' \
                 -H:+ReportUnsupportedElementsAtRuntime \
                 -H:+AllowVMInspection \
                 --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder',javax.net.ssl.SSLContext \
                 --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder,io.netty.handler.ssl.util.ThreadLocalInsecureRandom \
                 -H:-UseServiceLoaderFeature \
                 --allow-incomplete-classpath \
                 -H:Name=twitter-demo-kotlin \
                 -H:Class=fr.olivierrevial.Application


FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/gradle/twitter-demo-kotlin/twitter-demo-kotlin .
ENTRYPOINT ["./twitter-demo-kotlin"]
