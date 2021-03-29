import React, { useState, useEffect } from "react";
import externalResourceConfig from '../configs/externalResources.json'
import Table from 'react-bootstrap/Table';

function Holidays() {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const {HOLIDAYS_PATH: path, HOLIDAYS_SERVICE_URL: url, HOLIDAYS_COUNTRY: country, HOLIDAYS_YEAR: year} = externalResourceConfig;

    useEffect(() => {
    setLoading(true);
    fetch(`${url}/${path}/${year}/${country}`)
        .then(response => response.json())
        .then(setData)
        .then(() => setLoading(false))
        .catch(setError);
    }, []);

    if (loading) return <h1>Loading...</h1>;

    if (error) {
        return <pre>{JSON.stringify(error, null, 2)}</pre>;
    }

    if (data) {
        return (<Table striped bordered hover variant="dark">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Date</th>
                    <th>Holiday Name</th>
                </tr>
                </thead>
                <tbody>
                        {data.map((holiday, i) => { 
                            return <tr key={holiday.date}>
                                <td>{i}</td>
                                <td>{holiday.date}</td>
                                <td>{holiday.name}</td>
                            </tr>
                        })}
                        </tbody>
                </Table>);
    }

    return <div>No Data Available</div>;
  }
  
export default Holidays;
  