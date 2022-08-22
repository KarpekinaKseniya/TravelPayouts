import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from "react-bootstrap-table2-paginator";
import Card from 'react-bootstrap/Card';
import InputGroup from 'react-bootstrap/InputGroup'
import Form from 'react-bootstrap/Form'
import {BsSearch} from "react-icons/bs";

const columns = [
    {
        dataField: "title",
        text: "Title",
        sort: true
    },
    {
        dataField: "description",
        text: "Description",
        sort: true
    }
];


class PartnershipPrograms extends Component {

    state = {
        programs: []
    }

    async findAllPartnerShipPrograms(title) {
        const param = "/" + (title != null ? title : '');
        const response = await fetch('/travel-payouts/v1/partnership-programs' + param);
        const body = await response.json();
        this.setState({programs: body});
    }

    async handleChange(event) {
        let value = event.target.value;
        await this.findAllPartnerShipPrograms(value);
    }

    async componentDidMount() {
        await this.findAllPartnerShipPrograms(null);
    }

    render() {
        return (
            <Card style={{width: '90rem', marginLeft: '15rem'}}>
                <Card.Body>
                    <Card.Title>Partnership Programs</Card.Title>
                    <InputGroup className="mb-3">
                        <Form.Control
                            placeholder="Search by Title"
                            aria-describedby="basic-addon1"
                            onChange={this.handleChange.bind(this)}
                        />
                        <InputGroup.Text><BsSearch/></InputGroup.Text>
                    </InputGroup>
                    <BootstrapTable
                        striped
                        hover
                        keyField={"title"}
                        data={this.state.programs}
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

export default PartnershipPrograms;