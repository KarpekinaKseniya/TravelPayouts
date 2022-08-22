import 'bootstrap/dist/css/bootstrap.min.css';
import React, {Component} from "react";
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import ButtonToolbar from 'react-bootstrap/ButtonToolbar';

class HomePage extends Component {

    render() {
        const width = {width: '300px'};
        return (
            <div>
                <ButtonToolbar style={{
                    position: 'absolute', left: '50%', top: '40%',
                    transform: 'translate(-50%, -50%)'
                }}>
                    <ButtonGroup className="me-lg-5">
                        <Button variant="outline-primary" className="btn-lg" style={width}>Partnership
                            Programs</Button> {' '}
                    </ButtonGroup>
                    <ButtonGroup>
                        <Button variant="outline-warning" className="btn-lg" style={width}>Change Subscription
                            Status</Button> {' '}
                    </ButtonGroup>
                </ButtonToolbar>
                <ButtonToolbar style={{
                    position: 'absolute', left: '50%', top: '60%',
                    transform: 'translate(-50%, -50%)'
                }}>
                    <ButtonGroup className="me-lg-5">
                        <Button variant="outline-primary" className="btn-lg" style={width}>Users</Button> {' '}
                    </ButtonGroup>
                    <ButtonGroup>
                        <Button variant="outline-warning" className="btn-lg" style={width}>Block User
                            Program</Button> {' '}
                    </ButtonGroup>
                </ButtonToolbar>
            </div>
        );
    }
}

export default HomePage;