import React from 'react';
import { Space, Table, Tag } from 'antd';

// Định nghĩa cột dữ liệu
const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    render: (text) => <strong>{text}</strong>, 
  },
  {
    title: 'Tên thương hiệu',
    dataIndex: 'brand',
    key: 'brand',
  },
  {
    title: 'Mẫu mã',
    dataIndex: 'model',
    key: 'model',
  },
  {
    title: 'Mô tả ngắn',
    dataIndex: 'description',
    key: 'description',
    render: (text) => (
      <span style={{ whiteSpace: 'nowrap', textOverflow: 'ellipsis', overflow: 'hidden' }}>
        {text}
      </span>
    ),
  },
  {
    title: 'Địa điểm lắp đặt',
    dataIndex: 'location',
    key: 'location',
  },
  {
    title: 'Action',
    key: 'action',
    render: (_, record) => (
      <Space size="middle">
        <a>View Details</a>
        <a>Delete</a>
      </Space>
    ),
  },
];

// Dữ liệu máy in mẫu
const data = [
  {
    key: '1',
    id: 'P001',
    brand: 'Canon',
    model: 'PIXMA G2020',
    description: 'Máy in phun đa chức năng tiết kiệm mực, phù hợp cho văn phòng nhỏ.',
    location: 'Thư viện H1',
  },
  {
    key: '2',
    id: 'P002',
    brand: 'HP',
    model: 'LaserJet Pro M404dn',
    description: 'Máy in laser trắng đen, tốc độ cao, dễ sử dụng.',
    location: 'Thư viện H1',
  },
  {
    key: '3',
    id: 'P003',
    brand: 'Epson',
    model: 'EcoTank L3150',
    description: 'Máy in phun màu, in không dây, chi phí thấp.',
    location: 'Thư viện H1',
  },
]
const PrinterManagement = () => {
  return (
    <div style={{ padding: '20px' }}>
      <h2>Danh sách máy in</h2>
      <Table columns={columns} dataSource={data} />
    </div>
  );
};

export default PrinterManagement;


