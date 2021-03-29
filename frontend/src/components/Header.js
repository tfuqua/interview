import React from 'react'; 

import Nav from 'react-bootstrap/Nav';
import Row from 'react-bootstrap/Row';

function Header() {
      return (
        <>
        <Row className="justify-content-md-center">
          <h1>
              Holidays app
          </h1>
        </Row>
        <Row className="justify-content-md-center">
          <Nav>
          <Nav.Item>
            <Nav.Link href="/">Home</Nav.Link>
          </Nav.Item>
              <Nav.Link href='/holidays'>Holidays</Nav.Link>
          </Nav>
        </Row>
        </>
      );
  }

export default Header;