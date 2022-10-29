package md;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MainTest
{
    @Test
    void var_keyword()
    {
        var actual = 2 + 2;
        var expected = 4;
        assertThat(actual).isEqualTo(expected);
    }

    record BankAccount(String name, String number)
    {
        public BankAccount
        {
            checkArgument(name.length() > 10);
            checkArgument(number.length() > 8);
        }
    }

    @Test
    void records()
    {
        var account = new BankAccount("Marek Dudek", "123456789");
        assertThat(account.name).isEqualTo("Marek Dudek");
        assertThat(account.number).isEqualTo("123456789");
    }

    @Test
    void extended_switch()
    {
        var d = LocalDate.of(2022, 10, 29).getDayOfWeek();
        var free = switch (d)
                {
                    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> false;
                    case SATURDAY, SUNDAY -> true;
                };
        assertThat(free).isTrue();
    }

    @Test
    void extended_switch_with_code_block()
    {
        var d = LocalDate.of(2022, 10, 29).getDayOfWeek();
        var free = switch (d)
                {
                    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY ->
                    {
                        log.info("It is busy");
                        yield false;
                    }
                    case SATURDAY, SUNDAY ->
                    {
                        log.info("It is free");
                        yield true;
                    }
                };
        assertThat(free).isTrue();
    }
}
