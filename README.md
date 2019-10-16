# occupying

Korail API는 [korail2](https://github.com/carpedm20/korail2)를 참고하여 작성하였습니다.

## Using
좀더 나은 접근성을 위해 카카오 채널을 통한 챗봇으로 서비스를 이용할 수 있도록 구현하였습니다.

### 카카오 채널
![Alt Text](https://cdn-images-1.medium.com/max/1600/1*sn8RKubBYf34RYjQ4mN34g.png)<br/>
[홈 URL](http://pf.kakao.com/_YxoKNT)<br />
[채팅 URL](http://pf.kakao.com/_YxoKNT/chat)

### 챗봇
챗봇으로 [카카오 오픈빌더](https://i.kakao.com/openbuilder)를 사용하였습니다.<br />
자세한 내용은 카카오에서 제공하는 [도움말](https://i.kakao.com/docs/getting-started-overview#%EC%98%A4%ED%94%88%EB%B9%8C%EB%8D%94-%EC%86%8C%EA%B0%9C)을 참고하시기 바랍니다.

## Perparation
### `secret.yml` 파일 생성
`**/src/main/resources/`에 `secret.yml` 파일을 생성합니다.<br/>
해당 파일을 생성하지 않으면 서비스가 정상 동작하지 않습니다.
```
korail:
  id: {코레일계정}
  pw: {코레일비밀번호}
email:
  id: {이메일계정}
  pw: {이메일비밀번호}
```

## Installing
### `./gradlew build`
소스 파일을 패키징 합니다. <br/>
테스트를 실행하지 않게 하기 위해서는 `-x test` 옵션을 추가하면 됩니다.
### `java -jar build/lib/occupying-0.1.0.jar`
서비스를 실행합니다.

