import {useState, useEffect} from 'react';
import BookItem from '../../components/BookItem';

const Home = () => {
    const [books, setBooks] = useState([]);
    //useEffect는 함수 실행시 최초 한번만 실행됨
    //스프링부트 서버에 요청 (fetch 네트워크 통신 io 데이터를 받아옴), 비동기 함수
    //return에서 기본 랜더링 후 데이터를 다받아오면 다시 랜더링
    //기본적으로 CORS 정책 걸려있음. 서버에서 외부 자바스크립트 요청을 막음. springboot에서 풀어야함 
    useEffect(()=>{
        fetch("http://localhost:8080/book")
          .then(res=>res.json())
          .then(res=>{
            setBooks(res);
          });
    },[]);

    //key를 넣어야 변경된 부분만 랜더링됨.
    return (
        <div>
            {books.map((book) =>(
                <BookItem key={book.id} book={book}/>
            ))}
        </div>
    );
};

export default Home;