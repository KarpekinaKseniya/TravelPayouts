import React, {Component} from "react";
import {findAllUserPrograms, findAllUsers} from "../actions/UsersActions";
import {blockUserProgram} from "../actions/SubscriptionAction";
import {NotificationManager} from 'react-notifications';
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";

class BlockUserSubscription extends Component {

    state = {
        show: false,
        users: [],
        programs: []
    }

    async componentDidMount() {
        const users = await findAllUsers();
        const programs = await this.findProgramsTitle(users[0].id);
        this.setState({users: users, programs: programs});
    }

    async handleOnChange(event) {
        const userEmail = event.target.value;
        const user = this.state.users.filter((val) => val.email === userEmail);
        const programs = await this.findProgramsTitle(user[0].id);
        this.setState({programs: programs});
    }

    findProgramsTitle(userId) {
        return findAllUserPrograms(userId).then((body) => body.map(({title}) => title));
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
        const response = await blockUserProgram(userEmail, programTitle);
        if (response.status === 200) {
            await NotificationManager.success('User program is blocked successful.', 'Success', 2500);
        } else {
            const body = await response.json();
            await NotificationManager.error('User program didn\'t block.\nError status = ' + response.status + '.\nError message = ' + body.message, 'Error', 2500);
        }
        setTimeout(function () {
            window.location.reload()
        }, 2400);
    }

    render() {
        return (
            <>
                <Button variant="outline-warning" className="btn-lg" style={{width: '300px'}}
                        onClick={this.handleShow.bind(this)}>Block User Program</Button> {' '}
                <Modal
                    show={this.state.show}
                    onHide={this.handleClose.bind(this)}
                    backdrop="static"
                    keyboard={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Block User Program</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={this.handleSubmit.bind(this)}>
                            <Form.Group className="mb-3" controlId="user" onChange={this.handleOnChange.bind(this)}>
                                <Form.Select>
                                    {this.state.users.map((user) =>
                                        <option key={user.email}>{user.email}</option>)
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
                            <Button variant="primary" type="submit" disabled={this.state.programs.length === 0}>
                                Submit
                            </Button>
                        </Form>
                    </Modal.Body>
                </Modal>
            </>
        );
    }
}

export default BlockUserSubscription;