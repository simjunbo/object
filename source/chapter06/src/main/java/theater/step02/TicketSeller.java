package theater.step02;

/**
 * 묻지말고 시켜라
 */
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public TicketOffice getTicketOffice() {
        return ticketOffice;
    }

    public void setTicket(Audience audience) {
        ticketOffice.plusAmount(
                audience.setTicket(ticketOffice.getTicket())
        );
    }
}

