package space.app.link.shortener.server.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.grpc.server.service.GrpcService;
import space.app.link.shortener.server.entity.Link;
import space.app.link.shortener.server.repository.LinkRepository;
import spaceapp.linkshortener.*;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class LinkServiceImpl extends LinkServiceGrpc.LinkServiceImplBase {

    @Value("${link-shortener.base-url}/")
    private String baseUrl;

    private final LinkRepository linkRepository;

    @Override
    public void getShortLink(GetShortLinkRequest request, StreamObserver<GetShortLinkResponse> responseObserver) {
        String originalUrl = request.getOriginalUrl();

        String shortUrl = baseUrl + linkRepository.findByOriginalUrl(originalUrl)
                .map(Link::getShortUrl)
                .orElseGet(() -> {
                    String generated = UUID.randomUUID().toString().substring(0, 8);
                    linkRepository.save(Link.builder()
                            .originalUrl(originalUrl)
                            .shortUrl(generated)
                            .build());
                    return generated;
                });

        responseObserver.onNext(GetShortLinkResponse.newBuilder().setShortUrl(shortUrl).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getOriginalLink(GetOriginalLinkRequest request, StreamObserver<GetOriginalLinkResponse> responseObserver) {
        String shortUrl = request.getShortUrl();

        String originalUrl = linkRepository.findByShortUrl(shortUrl)
                .map(Link::getOriginalUrl)
                .orElse("");

        responseObserver.onNext(GetOriginalLinkResponse.newBuilder().setOriginalUrl(originalUrl).build());
        responseObserver.onCompleted();
    }
}
