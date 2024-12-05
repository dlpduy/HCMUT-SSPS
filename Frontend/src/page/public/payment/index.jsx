import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { callbackPayment } from "../../../api/shared";
import { Typography } from "antd";
const { Title } = Typography;

const Payment = () => {
  let status = "";
  const [isRequest, setIsRequest] = useState(false);
  const [type, setType] = useState();
  const location = useLocation();
  useEffect(() => {
    callbackPayment(location.search).then(() => setIsRequest(true));
  }, [isRequest]);
  const params = new URLSearchParams(location.search);
  const transactionStatus = params.get("vnp_TransactionStatus");
  switch (transactionStatus) {
    case "00":
      status = "Giao dịch thành công";
      setType("success");
      break;
    case "01":
      status = "Giao dịch chưa hoàn tất";
      setType("warning");
      break;
    case "02":
      status = "Giao dịch bị lỗi";
      setType("danger");
      break;
    case "04":
      status = "Giao dịch đảo (Khách hàng đã bị trừ tiền tại Ngân hàng nhưng GD chưa thành công ở VNPAY)";
      setType("warning");
      break;
    case "05":
      status = "VNPAY đang xử lý giao dịch này (GD hoàn tiền)";
      setType("warning");
      break;
    case "06":
      status = "VNPAY đã gửi yêu cầu hoàn tiền sang Ngân hàng (GD hoàn tiền)";
      setType("warning");
      break;
    case "07":
      status = "Giao dịch bị nghi ngờ gian lận";
      setType("warning");
      break;
    default:
      status = "GD Hoàn trả bị từ chối";
      setType("danger");
      break;
  }

  return transactionStatus && <Title type={type}>{status}</Title>;
};

export default Payment;
