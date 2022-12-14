import 'bootstrap/dist/css/bootstrap.min.css';
import React, {Component} from "react";
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import ButtonToolbar from 'react-bootstrap/ButtonToolbar';
import ChangeSubscriptionStatus from "./ChangeSubscriptionStatus";
import BlockUserSubscription from "./BlockUserSubscription";

class HomePage extends Component {

    render() {
        const width = {width: '300px'};
        return (
            <div>
                <ButtonToolbar  style={{
                    textAlign:'center',
                    justifyContent: 'center',
                    alignItems: 'center',
                    paddingLeft:40,
                    paddingRight:40,
                    marginTop:350,
                    height:40
                }}>
                    <ButtonGroup className="me-lg-5">
                        <Button variant="outline-primary" className="btn-lg" style={width}
                                onClick={() => window.location.href = '/partnership-programs'}>Partnership
                            Programs</Button> {' '}
                    </ButtonGroup>
                    <ButtonGroup>
                        <ChangeSubscriptionStatus/>
                    </ButtonGroup>
                </ButtonToolbar>
                <ButtonToolbar  style={{
                    textAlign:'center',
                    justifyContent: 'center',
                    alignItems: 'center',
                    paddingLeft:40,
                    paddingRight:40,
                    marginTop:40,
                    height:250
                }}>
                    <ButtonGroup className="me-lg-5">
                        <Button variant="outline-primary" className="btn-lg" style={width}
                                onClick={() => window.location.href = '/users'}>Users</Button> {' '}
                    </ButtonGroup>
                    <ButtonGroup>
                        <BlockUserSubscription/>
                    </ButtonGroup>
                </ButtonToolbar>
            </div>
        );
    }
}

export default HomePage;