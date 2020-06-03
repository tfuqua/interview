import React from 'react'

const UserComponent = ({user}) => {
    return (
        <tr>
            <th>{user.id}</th>
            <td>{user.name}</td>
            <td>{user.username}</td>
            <td>{user.email}</td>
            <td>{user.phone}</td>
            <td>{user.website}</td>
    </tr>
    )
};

export default UserComponent;