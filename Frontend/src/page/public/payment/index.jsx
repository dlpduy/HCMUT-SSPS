import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { callbackPayment } from "../../../api/shared";

const Payment = () => {
  let status = "";
  const [isRequest, setIsRequest] = useState(false);
  const location = useLocation();
  useEffect(() => {
    callbackPayment(location.search).then(() => setIsRequest(true));
  }, [isRequest]);
  const params = new URLSearchParams(location.search);
  const transactionStatus = params.get("vnp_TransactionStatus");
  switch (transactionStatus) {
    case "00":
      status = "Giao dịch thành công";
      break;
    case "01":
      status = "Giao dịch chưa hoàn tất";
      break;
    case "02":
      status = "Giao dịch bị lỗi";
      break;
    case "04":
      status = "Giao dịch đảo (Khách hàng đã bị trừ tiền tại Ngân hàng nhưng GD chưa thành công ở VNPAY)";
      break;
    case "05":
      status = "VNPAY đang xử lý giao dịch này (GD hoàn tiền)";
      break;
    case "06":
      status = "VNPAY đã gửi yêu cầu hoàn tiền sang Ngân hàng (GD hoàn tiền)";
      break;
    case "07":
      status = "Giao dịch bị nghi ngờ gian lận";
      break;
    default:
      status = "GD Hoàn trả bị từ chối";
      break;
  }

  return transactionStatus && <div>{status}</div>;
};

export default Payment;
