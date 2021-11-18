# React-Springboot 프로젝트

### 스프링 부트
- Springboot 2.0
- JPA
- H2 Database
- Maven
- Lombok
   
### 리액트
- yarn add react-router-dom
- yarn add redux react-redux
- yarn add react-bootstrap bootstrap
   
```
import 'bootstrap/dist/css/bootstrap.min.css';
```
    
react-router-dom이 버전 6로 업그레이드되면서,    
Switch를 더이상 지원을 안하게 됬다. Switch -> routes로 바뀜. 또한 component도 element로 바꼈다.   
공식문서 참조해서 코드를 조금 바꾸면 된다.   
```
// This is a React Router v6 app
import {
  BrowserRouter,
  Routes,
  Route,
  Link
} from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="users/*" element={<Users />} />
      </Routes>
    </BrowserRouter>
  );
}

function Users() {
  return (
    <div>
      <nav>
        <Link to="me">My Profile</Link>
      </nav>

      <Routes>
        <Route path=":id" element={<UserProfile />} />
        <Route path="me" element={<OwnUserProfile />} />
      </Routes>
    </div>
  );
}
```


