import { Container } from 'react-bootstrap';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Home from './pages/book/Home';
import Detail from './pages/book/Detail';
import SaveForm from './pages/book/SaveForm';
import UpdateForm from './pages/book/UpdateForm';


function App() {
  return (<div>
    <Header/>
    <Container>
      <Routes>
       <Route path="/" exact="{true}" element={<Home/>} />
       <Route path="/saveForm" exact="{true}" element={<SaveForm/>} />
       <Route path="/book:id" exact="{true}" element={<Detail/>} />
       <Route path="/joinForm" exact="{true}" element={''} />
       <Route path="/loginForm" exact="{true}" element={''} />
       <Route path="/updateForm" exact="{true}" element={<UpdateForm/>} />
      </Routes>
    </Container>
  </div>
  );
}

export default App;
