package edu.zcs.news;

import edu.zcs.news.proto.NewsProto;
import edu.zcs.news.proto.NewsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

public class NewsClient {
    private static final String host = "localhost";
    private static final int serverPort=9999;

    public static void main(String[] args) {
        //远程调用，创建网络通道
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort).usePlaintext()
                //无须加密或认证
                .build();
        try {
            NewsServiceGrpc.NewsServiceBlockingStub newBlockingStub = NewsServiceGrpc.newBlockingStub(channel);
            NewsProto.NewsRequest newsRequest = NewsProto.NewsRequest.newBuilder().setDate("20221127").build();
            NewsProto.NewsResponse newsResponse = newBlockingStub.list(newsRequest);
            List<NewsProto.News> newsList = newsResponse.getNewsList();
            newsList.forEach(item -> System.out.println(item.getTitle()+":"+item.getContent()));
        } finally {
            channel.shutdown();
        }
    }
}
