const { Option } = Select;
const { Header, Content } = Layout;

const PrinterSetting = () => {
  // State để lưu trạng thái lựa chọn dropdown và dữ liệu nhập
  const [selectedOption, setSelectedOption] = useState("Print-All-Pages");
  const [customPages, setCustomPages] = useState("");

  const handleOptionChange = (value) => {
    setSelectedOption(value);
  };

  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Content style={{ padding: "20px" }}>
        <div>
          <h3>Thiết lập bản in</h3>
          <Form layout="vertical">
            {/* Lựa chọn Trang in */}
            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Trang in">
                  <Select
                    defaultValue="Print-All-Pages"
                    onChange={handleOptionChange}
                  >
                    <Option value="Print-All-Pages">In tất cả các trang</Option>
                    <Option value="Select">Tùy chọn</Option>
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            {/* Hiển thị ô nhập khi chọn "Tùy chọn" */}
            {selectedOption === "Select" && (
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="Nhập trang muốn in (VD: 1, 3-5, 7)">
                    <Input
                      placeholder="Nhập số trang"
                      value={customPages}
                      onChange={(e) => setCustomPages(e.target.value)}
                    />
                  </Form.Item>
                </Col>
              </Row>
            )}

            {/* Loại giấy */}
            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Loại giấy">
                  <Select defaultValue="A4">
                    <Option value="A4">Kích thước A4</Option>
                    <Option value="A3">Kích thước A3</Option>
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            {/* Chế độ in */}
            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Chế độ in">
                  <Select defaultValue="Single-Sided">
                    <Option value="Single-Sided">In 1 mặt</Option>
                    <Option value="Duplex">In 2 mặt</Option>
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            {/* Hướng giấy */}
            <Row gutter={16}>
              <Col span={12}>
                <Form.Item label="Hướng giấy">
                  <Select defaultValue="portrait">
                    <Option value="portrait">Hướng giấy dọc</Option>
                    <Option value="landscape">Hướng giấy ngang</Option>
                  </Select>
                </Form.Item>
              </Col>
            </Row>
          </Form>
        </div>
      </Content>
    </Layout>
  );
};

export default PrinterSetting;
