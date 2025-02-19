# Get 
- GET은 요청을 전송할 때 필요한 데이터를 Body에 담지 않고, 쿼리스트링을 통해 전송
- URL의 끝에 ?와 함께 이름과 값으로 쌍을 이루는 요청 파라미터를 쿼리스트링이라고 부른다.
- 만약, 요청 파라미터가 여러 개이면 &로 연결함.
- 쿼리스트링을 사용하게 되면 URL에 조회 조건을 표시하기 때문에 특정 페이지를 링크하거나 북마크할 수 있다.
- 쿼리스트링을 포함한 URL의 샘플은 아래와 같다. 여기서 요청 파라미터명은 name1, name2이고, 각각의 파라미터는 value1, value2라는 값으로 서버에 요청을 보내게 된다.
    - www.example-url.com/resources?name1=value1&name2=value2
- 그리고 GET은 불필요한 요청을 제한하기 위해 요청이 캐시될 수 있다. js, css, 이미지 같은 정적 컨텐츠는 데이터양이 크고, 변경될 일이 적어서 반복해서 동일한 요청을 보낼 필요없다.
- 정적 컨텐츠를 요청하고 나면 브라우저에서는 요청을 캐시해두고, 동일한 요청이 발생할 때 서버로 요청을 보내지 않고 캐시된 데이터를 사용. 그래서 프론트엔드 개발을 하다보면 정적 컨텐츠가 캐시돼 컨텐츠를 변경해도 내용이 바뀌지 않는 경우가 종종 발생. 이 때는 브라우저의 캐시를 지워주면 다시 컨텐츠를 조회하기 위해 서버로 요청을 보내게 됨.
 
# GET의 특징 
- GET 요청은 캐시가 가능하다. 
- GET을 통해 서버에 리소스를 요청할 때 웹 캐시가 요청을 가로채 서버로부터 리소스를 다시 다운로드하는 대신 리소스의 복사본을 반환한다. HTTP 헤더에서 cache-control 헤더를 통해 캐시 옵션을 지정할 수 있다.
- GET 요청은 브라우저 히스토리에 남는다.
- GET 요청은 길이 제한이 있다.
- GET 요청은 중요한 정보를 다루면 안된다. (보안)

## POST
- POST는 리소스를 생성/변경하기 위해 설계되었기 때문에 GET과 달리 전송해야될 데이터를 HTTP 메세지의 Body에 담아서 전송. 
- HTTP 메세지의 Body는 길이의 제한없이 데이터를 전송할 수 있다. 그래서 POST 요청은 GET과 달리 대용량 데이터를 전송할 수 있음. 
- 이처럼 POST는 데이터가 Body로 전송되고 내용이 눈에 보이지 않아 GET보다 보안적인 면에서 안전하다고 생각할 수 있지만, POST 요청도 크롬 개발자 도구, Fiddler와 같은 툴로 요청 내용을 확인할 수 있기 때문에 민감한 데이터의 경우에는 반드시 암호화해 전송해야함.
- POST로 요청을 보낼 때는 요청 헤더의 Content-Type에 요청 데이터의 타입을 표시해야 합니다.
- Content-Type의 종류로는 application/x-www-form-urlencoded, text/plain, multipart/form-data 등이 있다
- 데이터 타입을 표시하지 않으면 서버는 내용이나 URL에 포함된 리소스의 확장자명 등으로 데이터 타입을 유추. 만약, 알 수 없는 경우에는 application/octet-stream로 요청을 처리함.

## POST 특징
- POST 요청은 캐시되지 않는다.
- POST 요청은 브라우저 히스토리에 남지 않는다.
- POST 요청은 데이터 길이에 제한이 없다.

# GET 과 POST 의 차이점 
- 사용목적 : GET은 서버의 리소스에서 데이터를 요청할 때, POST는 서버의 리소스를 새로 생성하거나 업데이트할 때 사용한다. DB로 따지면 GET은 SELECT 에 가깝고, POST는 Create 에 가깝다고 보면 된다.
- 요청에 body 유무 : GET 은 URL 파라미터에 요청하는 데이터를 담아 보내기 때문에 HTTP 메시지에 body가 없다. POST 는 body 에 데이터를 담아 보내기 때문에 당연히 HTTP 메시지에 body가 존재한다.
- 멱등성 (idempotent) : GET 요청은 멱등이며, POST는 멱등이 아니다.
    - 멱등 : 멱등의 사전적 정의는 연산을 여러 번 적용하더라도 결과가 달라지지 않는 성질을 의미한다.GET은 리소스를 조회한다는 점에서 여러 번 요청하더라도 응답이 똑같을 것 이다. 반대로 POST는 리소스를 새로 생성하거나 업데이트할 때 사용되기 때문에 멱등이 아니라고 볼 수 있다. (POST 요청이 발생하면 서버가 변경될 수 있다.)

### 질문
#### GET과 POST 요청에서 보안 측면에서의 차이점은 무엇인가요?
- GET 요청은 URL에 요청 데이터를 포함하므로 URL이 브라우저 히스토리에 남고 쉽게 노출될 수 있습니다. 반면 POST 요청은 데이터를 메시지의 Body에 담아 보내므로 URL에 데이터가 포함되지 않아 보안이 더 높은 것으로 간주됩니다. 하지만 POST 요청도 툴을 통해 내용을 확인할 수 있으므로 민감한 데이터는 반드시 암호화하여 전송해야 합니다.
#### GET과 POST가 멱등성 측면에서 어떻게 다른가요?
- GET 요청은 멱등성을 가지며, 여러 번 요청해도 서버 상태가 변하지 않고 같은 응답을 반환합니다. 그러나 POST 요청은 멱등하지 않으며, 서버의 리소스를 새로 생성하거나 변경하기 위해 사용되기 때문에 같은 요청을 여러 번 보내면 서버 상태가 달라질 수 있습니다.

참고링크 

https://brilliantdevelop.tistory.com/33