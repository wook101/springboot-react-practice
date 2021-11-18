import React from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <>
        <Navbar bg="dark" variant="dark">
            <Container>
            <Link to="/" className="navbar-brand">홈</Link>
            <Nav className="me-auto">
             <Link to="/saveForm" className="nav-link">글쓰기</Link>
             <Link to="/book:id" className="nav-link">상세보기</Link>
             <Link to="/updateForm" className="nav-link">수정하기</Link>
            </Nav>
            </Container>
        </Navbar>
        </>
    );
};

export default Header;