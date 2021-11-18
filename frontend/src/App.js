import { Container } from 'react-bootstrap';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';

function App() {
  return (<div>
    <Header/>
    <Container>
      <Routes>
       <Route path="/" exact="{true}" component={''} />
       <Route path="/saveFrom" exact="{true}" component={''} />
       <Route path="/book:id" exact="{true}" component={''} />
       <Route path="/joinFrom" exact="{true}" component={''} />
       <Route path="/loginFrom" exact="{true}" component={''} />
       <Route path="/updateFrom" exact="{true}" component={''} />
      </Routes>
    </Container>
  </div>
  );
}

export default App;
