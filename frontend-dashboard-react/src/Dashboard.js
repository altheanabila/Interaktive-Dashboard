import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
    Container,
    Typography,
    Paper,
    Grid,
    Card,
    CardContent,
    LinearProgress
} from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

const Dashboard = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [stats, setStats] = useState({
        totalProducts: 0,
        totalValue: 0,
        averagePrice: 0
    });

    // ✅ Use environment variable for API URL
    const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";
    axios.get(`${API_URL}/api/products`);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await axios.get(`${API_URL}/api/products`);
            const productsData = response.data;

            setProducts(productsData);

            // Calculate statistics
            const totalValue = productsData.reduce((sum, product) => sum + product.price * product.stockQuantity, 0);
            const totalProducts = productsData.length;
            const averagePrice = totalProducts > 0 ? totalValue / totalProducts : 0;

            setStats({
                totalProducts,
                totalValue,
                averagePrice
            });

            setLoading(false);
        } catch (error) {
            console.error('Error fetching data:', error);
            setLoading(false);
        }
    };

    // Define columns for the data grid
    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'name', headerName: 'Product Name', width: 200 },
        { field: 'description', headerName: 'Description', width: 300 },
        { field: 'price', headerName: 'Price', width: 120, type: 'number' },
        { field: 'category', headerName: 'Category', width: 150 },
        { field: 'stockQuantity', headerName: 'Stock', width: 100, type: 'number' }
    ];

    if (loading) {
        return <LinearProgress />;
    }

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom>
                Product Management Dashboard
            </Typography>

            {/* Statistics Cards */}
            <Grid container spacing={3} sx={{ mb: 3 }}>
                <Grid item xs={12} sm={4}>
                    <Card>
                        <CardContent>
                            <Typography color="textSecondary" gutterBottom>
                                Total Products
                            </Typography>
                            <Typography variant="h5" component="div">
                                {stats.totalProducts}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} sm={4}>
                    <Card>
                        <CardContent>
                            <Typography color="textSecondary" gutterBottom>
                                Total Inventory Value
                            </Typography>
                            <Typography variant="h5" component="div">
                                ${stats.totalValue.toFixed(2)}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} sm={4}>
                    <Card>
                        <CardContent>
                            <Typography color="textSecondary" gutterBottom>
                                Average Price
                            </Typography>
                            <Typography variant="h5" component="div">
                                ${stats.averagePrice.toFixed(2)}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>

            {/* Products Table */}
            <Paper sx={{ width: '100%' }}>
                <DataGrid
                    rows={products}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5, 10, 20]}
                    autoHeight
                    sx={{ border: 0 }}
                />
            </Paper>
        </Container>
    );
};

export default Dashboard;
