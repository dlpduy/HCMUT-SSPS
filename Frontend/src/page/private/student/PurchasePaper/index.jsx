import { useState, useEffect } from "react";
import { Button, Select, InputNumber, Table, Form } from "antd";
import { getAllPaper } from "../../../../api/shared";
import { getPageLeft, pagePurchase } from "../../../../api/student";

const { Option } = Select;

const PaperOrder = () => {
  const [form] = Form.useForm();

  const [paperList, setPaperList] = useState([]);
  const [needReload, setNeedReload] = useState(false);
  const [paperLeft, setPaperLeft] = useState(0);

  useEffect(() => {
    getAllPaper().then((res) => setPaperList(res.data));
  }, []);
  useEffect(() => {
    getPageLeft().then((res) => {
      setPaperLeft(res.data);
      setNeedReload(true);
    });
  }, [needReload]);

  const columns = [
    {
      title: <div className="text-darkblue">Loại giấy</div>,
      dataIndex: "paperType",
    },
    {
      title: <div className="text-darkblue">Số lượng</div>,
      dataIndex: "quantity",
    },
  ];
  const handleBuyPaper = (values) => {
    pagePurchase(values).then((res) => {
      if (res.message === "CALL API SUCCESS") window.location.href = res.data.paymentUrl;
    });
  };
  return (
    <div className="w-full h-full grid grid-cols-2 grid-rows-1 gap-5 bg-white p-5">
      {/* Cột Mua giấy in */}
      <div className="rounded-lg border p-5 bg-slate-50 bg-opacity-95 h-fit">
        <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Mua giấy in</div>
        <Form className="w-full" initialValues={{ paperType: "A4", quantity: 1 }} onFinish={handleBuyPaper} form={form}>
          <div className="text-base text-lightblue">Khổ giấy:</div>
          <Form.Item
            name="paperType"
            rules={[
              {
                required: true,
                message: "Cần phải nhập số lượng!",
              },
            ]}
          >
            <Select value={paperList[0]?.type}>
              {paperList.map((paper, index) => (
                <Option value={paper.type} key={index}>
                  {`${paper.type} - height:${paper.height} - width:${paper.width} - price:${paper.price}`}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <div className="text-base text-lightblue">Số lượng:</div>
          <Form.Item
            name="quantity"
            rules={[
              {
                required: true,
                message: "Cần phải nhập số lượng!",
              },
            ]}
          >
            <InputNumber placeholder="Nhập số lượng cần mua" className="w-full" />
          </Form.Item>
          <Form.Item>
            <Button htmlType="submit" className="w-1/2 bg-darkblue text-white font-bold text-base">
              Mua
            </Button>
          </Form.Item>
        </Form>
      </div>
      <div className="rounded-lg border p-5 bg-slate-50 bg-opacity-95 h-fit">
        {/* Cột Giỏ hàng */}
        <div className="rounded-lg border p-5 bg-slate-50 bg-opacity-95 h-fit">
          <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Số trang đã mua</div>
          <Table columns={columns} dataSource={paperLeft} pagination={false} loading={!needReload} />
        </div>
      </div>
    </div>
  );
};

export default PaperOrder;
