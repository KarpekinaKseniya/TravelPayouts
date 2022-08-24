import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from "react-bootstrap-table2-paginator";
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';
import UserInfo from "./UserInfo";
import CreateUser from "./CreateUser";
import {findAllUsers} from "../actions/UsersActions";

const columns = [
    {
        dataField: "name",
        text: "Username",
        sort: true
    },
    {
        dataField: "email",
        text: "Email",
        sort: true
    }
];


class Users extends Component {

    state = {
        users: [],
        showUserInfo: false,
        user: {
            id: 0,
            name: '',
            email: ''
        }
    }

    async componentDidMount() {
        const body = await findAllUsers();
        this.setState({users: body});
    }

    tableRowEvents = {
        onClick: (e, row, rowIndex) => {
            this.setState({showUserInfo: true, user: row});
        }
    }

    handleClose() {
        this.setState({showUserInfo: false});
    }


    render() {
        return (
            <div>
                <Card style={{width: '90rem', marginLeft: '15rem'}}>
                    <Card.Body>
                        <CreateUser/>
                        <BootstrapTable
                            striped
                            hover
                            keyField={"email"}
                            data={this.state.users}
                            columns={columns}
                            loading={true}
                            bootstrap4={true}
                            pagination={paginationFactory({sizePerPage: 10})}
                            rowEvents={this.tableRowEvents}
                        />
                    </Card.Body>
                </Card>
                <Modal show={this.state.showUserInfo} onHide={this.handleClose.bind(this)}>
                    <Modal.Header closeButton>
                        <Modal.Title>User Information</Modal.Title>
                    </Modal.Header>
                    <Modal.Body><UserInfo user={this.state.user}/></Modal.Body>
                </Modal>
            </div>
        );
    }
}

export default Users;