import React, {Component} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import {findAllUsers} from "../actions/UsersActions";
import {findAllPartnerShipPrograms} from "../actions/ProgramsActions";
import {changeSubscriptionStatus} from "../actions/SubscriptionAction";
import {NotificationManager} from "react-notifications";

class ChangeSubscriptionStatus extends Component {

    state = {
        show: false,
        users: [],
        programs: [],
        subscribeStatus: ['SUBSCRIBED', 'UNSUBSCRIBED', 'BLOCK']
    }

    async componentDidMount() {
        const users = await findAllUsers().then((body) => body.map(({email}) => email));
        const programs = await findAllPartnerShipPrograms(null).then((body) => body.map(({title}) => title));
        this.setState({users: users, programs: programs});
    }

    handleShow() {
        this.setState({show: true});
    }

    handleClose() {
        this.setState({show: false});
    }

    async handleSubmit(event) {
        event.preventDefault();
        event.stopPropagation();
        const userEmail = event.target.user.value;
        const programTitle = event.target.program.value;
        const subscribeStatus = event.target.status.value;
        const response = await changeSubscriptionStatus(userEmail, programTitle, subscribeStatus);
        if (response.status === 200) {
            await NotificationManager.success('Subscription status is changed successful.', 'Success', 2500);
        } else {
            const body = await response.json();
            await NotificationManager.error('Subscription status didn\'t change.\nError status = ' + response.status + '.\nError message = ' + body.message, 'Error', 2500);
        }
        setTimeout(function () {
            window.location.reload()
        }, 2400);
    }

    render() {
        return (
            <>
                <Button variant="outline-warning" className="btn-lg" style={{width: '300px'}}
                        onClick={this.handleShow.bind(this)}>Change Subscription
                    Status</Button> {' '}
                <Modal
                    show={this.state.show}
                    onHide={this.handleClose.bind(this)}
                    backdrop="static"
                    keyboard={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Change Subscription Status</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={this.handleSubmit.bind(this)}>
                            <Form.Group className="mb-3" controlId="user">
                                <Form.Select>
                                    {this.state.users.map((user) =>
                                        <option key={user}>{user}</option>)
                                    }
                                </Form.Select>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="program">
                                <Form.Select>
                                    {this.state.programs.map((program) =>
                                        <option key={program}>{program}</option>)
                                    }
                                </Form.Select>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="status">
                                <Form.Select>
                                    {this.state.subscribeStatus.map((status) =>
                                        <option key={status}>{status}</option>)
                                    }
                                </Form.Select>
                            </Form.Group>
                            <Button variant="primary" type="submit">
                                Submit
                            </Button>
                        </Form>
                    </Modal.Body>
                </Modal>
            </>
        );
    }
}

export default ChangeSubscriptionStatus;