import React, {Component} from "react";
import Table from 'react-bootstrap/Table';

class UserInfo extends Component {

    state = {
        programs: [],
        hasPrograms: false
    }

    async findAllUserPrograms(id) {
        const url = '/travel-payouts/v1/user/' + id + '/programs';
        const response = await fetch(url);
        const body = await response.json();
        const hasSubscriptions = body.length !== 0;
        this.setState({programs: body, hasPrograms: hasSubscriptions});
    }

    async componentDidMount() {
        await this.findAllUserPrograms(this.props.user.id);
    }


    render() {
        return (
            <div>
                <h6>Name: {this.props.user.name}</h6>
                <h6>Email: {this.props.user.email}</h6>
                <h6>Subscriptions</h6>
                <Table striped bordered hover variant="dark">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.hasPrograms ?
                        (
                            Array.from(this.state.programs).map((program, index) => (
                                <tr key={index}>
                                    <td>
                                        {program.title}
                                    </td>
                                    <td>
                                        {program.description}
                                    </td>
                                </tr>
                            ))
                        ) : (<tr>
                            <td colSpan={2}>Doesn't have some subscriptions.</td>
                        </tr>)
                    }
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default UserInfo;