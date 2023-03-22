// TODO 거래내역 박스
// TODO 무한 스크롤 구현하기

import { FC, useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { MdFastfood } from 'react-icons/md';
import { BiCoffeeTogo } from 'react-icons/bi';
import { IoLogoGameControllerA } from 'react-icons/io';
import Modal from '../transaction/Modal';
import axios from 'axios';

// axios GET 요청으로 불러온 데이터 타입 정의
export interface Transaction {
  amount: number;
  branchName: string;
  date: Date;
  id: number;
  inoutType: string;
  memberCategoryId: number;
  memo: string;
  time: string;
}

export interface Props {
  transactions: Transaction[];
  date: Date;
}

// 카테고리별 아이콘 설정하기 <카테고리명: 아이콘이름>
const categoryIcons: Record<string, any> = {
  식비: MdFastfood,
  음료: BiCoffeeTogo,
  오락: IoLogoGameControllerA,
};

// 카테고리별 아이콘 색상 설정하기
const CategoryIconWrapper = styled.div<{ category: string }>`
  max-width: fit-content;
  margin-left: 20px;
  padding: 10px;
  border-radius: 100%;
  color: white;
  font-size: 20px;
  background-color: ${(props) => {
    switch (props.category) {
      case '식비':
        return '#FFC107'; // yellow
      case '음료':
        return '#4CAF50'; // green
      case '오락':
        return '#9C27B0'; // purple
      default:
        return 'inherit';
    }
  }};
`;
const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const TransactionContainer = styled.div`
  padding: 10px;
  .transaction-date-box {
    text-align: left;
    margin-top: 10px;
    margin-bottom: 10px;
    color: grey;
  }
`;

const ContentContainer = styled.div`
  display: flex;
  .transaction-content-box {
    margin-left: 20px;
    text-align: left;
  }

  .transaction-content-box:hover {
    cursor: pointer;
  }
  .transaciton-bank-box {
    color: grey;
  }
`;

// 날짜+요일별 거래내역 박스
const Transaction: FC<Props> = ({ transactions, date }) => {
  // 거래내역 상태 관리
  const [transactionHistories, setTransactionHistories] = useState<
    Transaction[]
  >([
    {
      amount: 0,
      branchName: '',
      date: new Date(date),
      id: 0,
      inoutType: '',
      memberCategoryId: 0,
      memo: '',
      time: '',
    },
  ]);
  // 거래내역 네트워크 요청
  // TODO 헤더의 month를 params로 입력
  useEffect(() => {
    axios
      .get<Transaction[]>('http://localhost:8080/transactionHistoriesResponse')
      .then((res) => {
        const data: Transaction[] = res.data;
        setTransactionHistories(
          // new Date 처리를 하지 않으면, date 가 string 타입으로 들어감.
          data.map((transaction) => ({
            ...transaction,
            date: new Date(transaction.date),
          }))
        );
      })
      .catch((err) => console.log(err));
  }, []);

  // 거래내역 클릭 시 상세 정보 모달 띄우기
  const [showModal, setShowModal] = useState<boolean>(false);
  const toggleModal = () => {
    setShowModal(!showModal);
  };
  const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

  // //헤더의 Month
  // let headerMonth = date.getMonth() + 1;

  // //거래내역의 Month
  // let transactioinMonth = transactions[0].date.getMonth() + 1;
  // console.log(transactions[0].date.getMonth() + 1);

  // 헤더에서 선택한 달과 일치하는 거래내역만 가져오기
  // const transactionThisMonth = transactionHistories.filter(
  //   (el) => el.date.getMonth() === headerMonth
  // );

  //TODO 무한스크롤
  const options = {
    root: null,
    rootMargin: '10px',
    threshold: 0.5,
  };

  const callback = () => {
    console.log('관측되었습니다.');
  };

  let target = useRef<HTMLDivElement>(null);

  useEffect(() => {
    let observer = new IntersectionObserver(callback, options);
    if (target.current) {
      observer.observe(target.current as Element);
    }
    return () => observer && observer.disconnect();
  }, []);

  return (
    <Container className="transaction-history-box" ref={target}>
      {transactionHistories.map((transaction, index) => {
        const IconComponent = categoryIcons[transaction.memberCategoryId];
        return (
          <TransactionContainer key={transaction.id} onClick={toggleModal}>
            <div>
              {/* <div className="transaction-date-box">
                {transaction.date.getDate()}일{' '}
                {daysOfWeek[transaction.date.getDay()]}요일
              </div> */}
              <ContentContainer>
                {/* <CategoryIconWrapper category={transaction.memberCategoryId}>
                  <IconComponent className="transaciton-icon" />
                </CategoryIconWrapper> */}
                <div className="transaction-content-box" onClick={toggleModal}>
                  {showModal && <Modal transaction={transaction}></Modal>}
                  <div className="transaction-amount-box">
                    <strong>{transaction.amount}원</strong>
                  </div>
                  <div className="transaciton-bank-box">
                    {transaction.branchName} &#8594; {transaction.branchName}
                  </div>
                </div>
              </ContentContainer>
            </div>
          </TransactionContainer>
        );
      })}
    </Container>
  );
};

export default Transaction;
