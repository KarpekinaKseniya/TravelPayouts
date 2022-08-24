import React, {Component} from "react";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import {createUser} from "../actions/UsersActions";

class CreateUser extends Component {

    state = {
        show: false,
        validated: false
    }

    handleClose() {
        this.setState({show: false});
    }

    handleShow() {
        this.setState({show: true});
    }

    async handleSubmit(event) {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            const user = {
                email: event.target.email.value,
                name: event.target.name.value
            }
            const response = await createUser(user);
            const body = await response.json();
            if (response.status === 201) {
                alert('User created success with id = ' + body);
            } else {
                alert('User didn\'t created.\nError status = ' + response.status + '.\nError message = ' + body.message);
            }
        }
        this.setState({validated: true});
    }

    render() {
        return (
            <div>
                <Button variant="primary" onClick={this.handleShow.bind(this)} className="mb-3">
                    Add new user
                </Button>
                <Modal show={this.state.show} onHide={this.handleClose.bind(this)}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create User</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit.bind(this)}>
                            <Form.Group className="mb-3" controlId="name">
                                <Form.Label>Username</Form.Label>
                                <Form.Control required type="text" placeholder="Username"/>
                                <Form.Control.Feedback type="invalid">
                                    Please provide username.
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="email">
                                <Form.Label>Email address</Form.Label>
                                <Form.Control required type="text" placeholder="Enter email"/>
                                <Form.Control.Feedback type="invalid">
                                    Please provide email address.
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Button variant="primary" type="submit" className="col-md-12 text-center">
                                Submit
                            </Button>
                        </Form>
                    </Modal.Body>
                </Modal>
            </div>
        );
    }
}

export default CreateUser;