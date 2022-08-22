import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from "react-bootstrap-table2-paginator";
import Card from 'react-bootstrap/Card';

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
        users: []
    }

    async findAllUsers() {
        const response = await fetch('/travel-payouts/v1/users');
        const body = await response.json();
        this.setState({users: body});
    }

    async handleChange(event) {
        let value = event.target.value;
        await this.findAllUsers(value);
    }

    async componentDidMount() {
        await this.findAllUsers(null);
    }

    render() {
        return (
            <Card style={{width: '90rem', marginLeft: '15rem'}}>
                <Card.Body>
                    <BootstrapTable
                        striped
                        hover
                        keyField={"title"}
                        data={this.state.users}
                        columns={columns}
                        loading={true}
                        bootstrap4={true}
                        pagination={paginationFactory({sizePerPage: 10})}
                    />
                </Card.Body>
            </Card>
        );
    }
}

export default Users;