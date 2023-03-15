//TODO SIGNUP_002 유저 정보 입력 페이지 구현 (사용자 이름, 예산 시작일, 급여일, 고정 지출 등)
import { useState } from 'react';

import styled from 'styled-components';
import {
  Input,
  InputGroup,
  InputLeftElement,
  InputRightAddon,
} from '@chakra-ui/react';

import { Button } from '@chakra-ui/react';
import { Icon, AddIcon } from '@chakra-ui/icons';
import { BsFillPersonFill } from 'react-icons/bs';
import { TbPigMoney } from 'react-icons/tb';
import { GiReceiveMoney, GiExpense } from 'react-icons/gi';

const Container = styled.div`
  display: flex;
  flex-direction: column;

  h1 {
    margin-top: 100px;
    margin-bottom: 8em;
    text-align: left;
  }

  input {
    width: auto;
    margin-bottom: 8em;
  }
`;

const SetContainer = styled.div`
  display: flex;
`;
// TODO 체크 아이콘 조건부 색상 변경 (green)
// TODO 금액 입력 시 콤마 찍혀서 input에 출력
function UserSetting() {
  // username
  const [username, setUsername] = useState('');
  // initial budget
  const [ibDate, setIbDate] = useState();
  const [ibAmount, setIbAmount] = useState();
  // salary
  const [salaryDate, setSalaryDate] = useState();
  const [salaryAmount, setSalaryAmount] = useState();
  // fixed expenses
  const [feDate, setFeDate] = useState();
  const [feAmount, setFeAmount] = useState();

  const handleUserInput = (e: any) => {
    setUsername(e.target.value);
  };

  const handleIbAmount = (e: any) => {
    setIbAmount(e.target.value);
  };

  const handleSalaryAmount = (e: any) => {
    setSalaryAmount(e.target.value);
  };

  const handleFeAmount = (e: any) => {
    setFeAmount(e.target.value);
  };
  return (
    <Container>
      <h1>회원 정보 설정</h1>
      <SetContainer>
        <InputGroup className="input-username">
          <InputLeftElement
            pointerEvents="none"
            children={<Icon as={BsFillPersonFill} color="gray.400" />}
          />
          <Input
            type="text"
            size="sm"
            focusBorderColor="purple.400"
            onKeyUp={(e) => {
              handleUserInput(e);
            }}
          ></Input>
          {/* <InputRightElement children={<CheckIcon color="gray.300" />} /> */}
        </InputGroup>
        <Button
          colorScheme="purple"
          size="md"
          variant="outline"
          leftIcon={<AddIcon />}
          loadingText="연결 중"
        >
          계좌 연결하기
        </Button>
      </SetContainer>
      <SetContainer>
        <InputGroup className="input-initialbudget">
          <InputLeftElement
            pointerEvents="none"
            children={<Icon as={TbPigMoney} color="gray.400" />}
          />
          <Input type="date" size="sm" focusBorderColor="purple.400"></Input>
        </InputGroup>
        <InputGroup className="input-initialbudget" size="sm">
          <InputLeftElement
            pointerEvents="none"
            children={<Icon as={TbPigMoney} color="gray.400" />}
          />
          <Input
            type="number"
            focusBorderColor="purple.400"
            onKeyUp={(e) => handleIbAmount(e)}
            value={ibAmount}
          ></Input>
          <InputRightAddon children="원" />
        </InputGroup>
      </SetContainer>

      <SetContainer>
        <InputGroup className="input-salary">
          <InputLeftElement
            pointerEvents="none"
            children={<Icon as={GiReceiveMoney} color="gray.400" />}
          />
          <Input type="date" size="sm" focusBorderColor="purple.400"></Input>
        </InputGroup>
        <InputGroup className="input-salary" size="sm">
          <InputLeftElement
            pointerEvents="none"
            children={<Icon as={GiReceiveMoney} color="gray.400" />}
          />
          <Input
            type="number"
            focusBorderColor="purple.400"
            onKeyUp={(e) => handleSalaryAmount(e)}
          ></Input>
          <InputRightAddon children="원" />
        </InputGroup>
      </SetContainer>
      <SetContainer>
        <InputGroup className="input-fixedexpenses">
          <InputLeftElement
            pointerEvents="none"
            children={<Icon as={GiExpense} color="gray.400" />}
          />
          <Input type="date" size="sm" focusBorderColor="purple.400"></Input>
        </InputGroup>
        <InputGroup className="input-fixedexpenses" size="sm">
          <InputLeftElement
            pointerEvents="none"
            children={<Icon as={GiExpense} color="gray.400" />}
          />
          <Input
            type="number"
            focusBorderColor="purple.400"
            onKeyUp={(e) => handleFeAmount(e)}
          ></Input>
          <InputRightAddon children="원" />
        </InputGroup>
      </SetContainer>
      <Button colorScheme="purple" size="md" variant="outline">
        저장하기
      </Button>
    </Container>
  );
}

export default UserSetting;
