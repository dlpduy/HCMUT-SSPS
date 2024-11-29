import React, { useState } from 'react';
import { Button, Select, InputNumber, Typography, Table } from 'antd';

const { Text } = Typography;
const { Option } = Select;

const PaperOrder = () => {
  const [paperSize, setPaperSize] = useState("A4");
  const [quantity, setQuantity] = useState(10);
  const [totalAmount, setTotalAmount] = useState(10000);
  const pricePerItem = 1000;

  const columns = [
    {
      title: <Text style={{ color: 'blue' }}>Khổ giấy</Text>,
      dataIndex: 'size',
      key: 'size',
    },
    {
      title: <Text style={{ color: 'blue' }}>Số lượng</Text>,
      dataIndex: 'quantity',
      key: 'quantity',
    },
    {
      title: <Text style={{ color: 'blue' }}>Thành tiền (VND)</Text>,
      dataIndex: 'price',
      key: 'price',
    },
  ];

  const data = [
    {
      key: '1',
      size: 'A3',
      quantity: 5,
      price: '10,000',
    },
    {
      key: '2',
      size: 'A4',
      quantity: 10,
      price: '10,000',
    },
    {
      key: '3',
      size: 'A5',
      quantity: 20,
      price: '10,000',
    },
  ];

  const handleAddToCart = () => {
    setTotalAmount(quantity * pricePerItem); // Cập nhật tổng tiền
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'space-between', padding: '20px' }}>
      {/* Cột Mua giấy in */}
      <div style={{ width: '45%', padding: '20px', border: '1px solid #ddd', borderRadius: '8px' }}>
        <Text strong style={{ fontSize: '20px', color: 'blue' }}>Mua giấy in</Text>
        <div style={{ marginTop: '20px' }}>
          <div style={{ marginBottom: '10px' }}>
            <Text style={{ color: 'blue' }}>Khổ giấy:</Text>
            <Select
              value={paperSize}
              onChange={(value) => setPaperSize(value)}
              style={{ marginBottom: 10, width: '100%' }}
            >
              <Option value="A3">A3</Option>
              <Option value="A4">A4</Option>
              <Option value="A5">A5</Option>
            </Select>
          </div>

          <div style={{ marginBottom: '10px' }}>
            <Text style={{ color: "blue" }}>Số lượng:</Text>
            <InputNumber
              min={1}
              value={quantity}
              onChange={(value) => setQuantity(value)}
              style={{ marginBottom: 10, width: '100%' }}
            />
          </div>

          <div style={{ marginBottom: '10px' }}>
            <Text>
              <span style={{ color: "blue" }}>Thành tiền:</span>
              <span style={{ marginLeft: 5 }}>
                {quantity * pricePerItem} VND
              </span>
            </Text>
          </div>

          <Button type="primary" onClick={handleAddToCart} style={{ width: '100%' }}>
            Thêm
          </Button>
        </div>
      </div>

      {/* Cột Giỏ hàng */}
      <div style={{ width: '45%', padding: '20px', border: '1px solid #ddd', borderRadius: '8px' }}>
        <Text strong style={{ fontSize: '20px', color: 'black' }}>Giỏ hàng</Text>
        <Table
          columns={columns}
          dataSource={data}
          pagination={false}
          style={{ marginTop: '20px', marginBottom: 10 }}
        />
      </div>
    </div>

    {/* Tổng cộng và các nút dưới cùng */}
    <div style={{ textAlign: 'center', marginTop: '20px' }}>
      <div style={{ display: 'flex', justifyContent: 'center', gap: '10px', alignItems: 'center' }}>
        <Text style={{ fontSize: '16px', fontWeight: '600', color: 'blue' }}>Tổng cộng:</Text>
        <Text style={{ fontSize: '16px', fontWeight: '600' }}>
          {totalAmount.toLocaleString()} VND
        </Text>
      </div>

      <div style={{ marginTop: '10px', display: 'flex', justifyContent: 'center', gap: '10px' }}>
        <Button type="primary" style={{ width: 120 }}>
          Xác nhận
        </Button>
        <Button danger style={{ width: 120 }}>
          Hủy bỏ
        </Button>
      </div>
    </div>
  );
};

export default PaperOrder;
