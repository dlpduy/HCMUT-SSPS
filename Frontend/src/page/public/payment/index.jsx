import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { callbackPayment } from "../../../api/shared";
import { Typography } from "antd";
const { Title } = Typography;

const Payment = () => {
  const [status, setStatus] = useState("");
  const [type, setType] = useState("");
  const location = useLocation();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search || "");
    const transactionStatus = searchParams.get("vnp_TransactionStatus");

    switch (transactionStatus) {
      case "00":
        setStatus("Giao dịch thành công");
        setType("success");
        break;
      case "01":
        setStatus("Giao dịch chưa hoàn tất");
        setType("warning");
        break;
      case "02":
        setStatus("Giao dịch bị lỗi");
        setType("danger");
        break;
      case "04":
        setStatus("Giao dịch đảo (Khách hàng đã bị trừ tiền tại Ngân hàng nhưng GD chưa thành công ở VNPAY)");
        setType("warning");
        break;
      case "05":
        setStatus("VNPAY đang xử lý giao dịch này (GD hoàn tiền)");
        setType("warning");
        break;
      case "06":
        setStatus("VNPAY đã gửi yêu cầu hoàn tiền sang Ngân hàng (GD hoàn tiền)");
        setType("warning");
        break;
      case "07":
        setStatus("Giao dịch bị nghi ngờ gian lận");
        setType("warning");
        break;
      default:
        setStatus("GD Hoàn trả bị từ chối");
        setType("danger");
        break;
    }
    callbackPayment(location.search).catch((error) => {
      console.error("Callback Payment Error: ", error);
    });
  }, [location.search]);

  return status ? <Title type={type}>{status}</Title> : null;
};

export default Payment;
