import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('All');
    const [sortBy, setSortBy] = useState('name');
    const [editingProduct, setEditingProduct] = useState(null);
    const [showForm, setShowForm] = useState(false);

    const API_URL = process.env.REACT_APP_API_URL;

    useEffect(() => {
        fetchProducts();
    }, []);

    useEffect(() => {
        filterAndSortProducts();
    }, [products, searchTerm, selectedCategory, sortBy]);

    const fetchProducts = async () => {
        try {
            const response = await axios.get(`${API_URL}/api/products`);
            setProducts(response.data);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching data:', error);
            setLoading(false);
        }
    };

    const filterAndSortProducts = () => {
        let filtered = products.filter(product =>
            product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            product.description.toLowerCase().includes(searchTerm.toLowerCase())
        );

        if (selectedCategory !== 'All') {
            filtered = filtered.filter(product => product.category === selectedCategory);
        }

        filtered.sort((a, b) => {
            switch (sortBy) {
                case 'price': return a.price - b.price;
                case 'name': return a.name.localeCompare(b.name);
                case 'stock': return a.stockQuantity - b.stockQuantity;
                default: return 0;
            }
        });

        setFilteredProducts(filtered);
    };

    const handleEdit = (product) => {
        setEditingProduct({ ...product });
        setShowForm(true);
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this product?')) {
            try {
                await axios.delete(`${API_URL}/api/products/${id}`);
                fetchProducts();
                alert('Product deleted successfully!');
            } catch (error) {
                console.error('Error deleting product:', error);
                alert('Error deleting product');
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editingProduct.id) {
                // Update existing product
                await axios.put(
                    `${API_URL}/api/products/${editingProduct.id}`,
                    editingProduct,
                    { headers: { 'Content-Type': 'application/json' } }
                );
            } else {
                // Create new product
                await axios.post(
                    `${API_URL}/api/products`,
                    editingProduct,
                    { headers: { 'Content-Type': 'application/json' } }
                );
            }

            setShowForm(false);
            setEditingProduct(null);
            fetchProducts();
            alert('Product saved successfully!');
        } catch (error) {
            console.error('Error saving product:', error);
            alert('Error saving product');
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditingProduct(prev => ({
            ...prev,
            [name]: name === 'price' || name === 'stockQuantity' ? parseFloat(value) : value
        }));
    };

    const categories = ['All', ...new Set(products.map(p => p.category))];

    if (loading) return <div className="App">Loading products...</div>;

    return (
        <div className="App">
            <h1>Project Nexus Dashboard</h1>

            <button
                onClick={() => {
                    setEditingProduct({
                        name: '',
                        description: '',
                        price: 0,
                        category: '',
                        stockQuantity: 0
                    });
                    setShowForm(true);
                }}
                className="btn-add"
            >
                + Add New Product
            </button>

            <div className="controls">
                <input
                    type="text"
                    placeholder="Search products..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="search-input"
                />

                <select
                    value={selectedCategory}
                    onChange={(e) => setSelectedCategory(e.target.value)}
                    className="filter-select"
                >
                    {categories.map(category => (
                        <option key={category} value={category}>{category}</option>
                    ))}
                </select>

                <select
                    value={sortBy}
                    onChange={(e) => setSortBy(e.target.value)}
                    className="sort-select"
                >
                    <option value="name">Sort by Name</option>
                    <option value="price">Sort by Price</option>
                    <option value="stock">Sort by Stock</option>
                </select>
            </div>

            {showForm && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h2>{editingProduct.id ? 'Edit Product' : 'Create Product'}</h2>
                        <form onSubmit={handleSubmit}>
                            <input
                                name="name"
                                placeholder="Name"
                                value={editingProduct.name}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                name="description"
                                placeholder="Description"
                                value={editingProduct.description}
                                onChange={handleInputChange}
                            />
                            <input
                                name="price"
                                type="number"
                                placeholder="Price"
                                value={editingProduct.price}
                                onChange={handleInputChange}
                            />
                            <input
                                name="category"
                                placeholder="Category"
                                value={editingProduct.category}
                                onChange={handleInputChange}
                            />
                            <input
                                name="stockQuantity"
                                type="number"
                                placeholder="Stock Quantity"
                                value={editingProduct.stockQuantity}
                                onChange={handleInputChange}
                            />
                            <button type="submit">Save</button>
                        </form>
                    </div>
                </div>
            )}

            <div className="stats">
                <div className="stat-card">
                    <h3>Total Products</h3>
                    <p>{filteredProducts.length}</p>
                </div>
                <div className="stat-card">
                    <h3>Total Value</h3>
                    <p>${filteredProducts.reduce((sum, p) => sum + p.price * p.stockQuantity, 0).toFixed(2)}</p>
                </div>
                <div className="stat-card">
                    <h3>Average Price</h3>
                    <p>${(filteredProducts.reduce((sum, p) => sum + p.price, 0) / filteredProducts.length || 0).toFixed(2)}</p>
                </div>
            </div>

            <div className="products-container">
                {filteredProducts.length === 0 ? (
                    <p>No products found matching your criteria.</p>
                ) : (
                    filteredProducts.map(product => (
                        <div key={product.id} className="product-card">
                            <h3>{product.name}</h3>
                            <p className="description">{product.description}</p>
                            <p><strong>Price:</strong> ${product.price}</p>
                            <p><strong>Category:</strong> {product.category}</p>
                            <p><strong>Stock:</strong> {product.stockQuantity}</p>
                            <div className="product-actions">
                                <button className="btn-edit" onClick={() => handleEdit(product)}>Edit</button>
                                <button className="btn-delete" onClick={() => handleDelete(product.id)}>Delete</button>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

export default App;
